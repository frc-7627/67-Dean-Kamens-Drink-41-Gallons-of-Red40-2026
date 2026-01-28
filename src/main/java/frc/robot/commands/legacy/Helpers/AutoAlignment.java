package frc.robot.commands.legacy.Helpers;

import java.util.logging.Logger;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.DrivebaseConstants;
import frc.robot.commands.IndicatingCommandWrapper;
import frc.robot.commands.ProgressingCommand;
// import frc.robot.subsystems.Bluetooth; TODO: Reimplement this with indication, Comments of where
// it is used are spread througout this file
import frc.robot.commands.util.Progress;
import frc.robot.subsystems.Indicator;
import frc.robot.subsystems.legacy.vision.OldVision;
import frc.robot.subsystems.legacy.SwerveSubsystem;

public class AutoAlignment extends ProgressingCommand<AutoAlignmentState> {
    PhotonCamera Left_PI_CAM = new PhotonCamera("PC_Camera SIG");
    PhotonCamera Right_PI_CAM = new PhotonCamera("PC_Camera MA");
    private final SwerveSubsystem drivebase;
    private final OldVision vision;
    // private final Bluetooth led;
    private boolean leftcam;
    private double user_offset;
    private Command driveCommand;

    public static int autoAliging;

    private static final Logger LOGGER =
            Logger.getLogger(DriveBaseRotationAdjust.class.getSimpleName());

    public AutoAlignment(Indicator indicator, SwerveSubsystem module,
            /* Bluetooth led, */ double offset, boolean leftcam) {
        super(LOGGER, indicator, AutoAlignmentState.LOOKING_FOR_TARGET);
        this.drivebase = module;
        this.vision = drivebase.getVision();
        // this.led = led;
        user_offset = offset;
        this.leftcam = leftcam;
        addRequirements(drivebase);
        // addRequirements(led);
    }

    /** Run once at Command Start */
    @Override
    public void initialize() {
        autoAliging = 2;
        SmartDashboard.putNumber("Commands/AutoAlign", autoAliging); // debug

        DrivebaseConstants.x_offset =
                SmartDashboard.getNumber("Vision/x_offset", DrivebaseConstants.x_offset);
        DrivebaseConstants.y_offset =
                SmartDashboard.getNumber("Vision/y_offset", DrivebaseConstants.y_offset);
        DrivebaseConstants.y_offset_left =
                SmartDashboard.getNumber("Y_Offset_Left", DrivebaseConstants.y_offset_left);
        DrivebaseConstants.y_offset_right =
                SmartDashboard.getNumber("Y_Offset_Right", DrivebaseConstants.y_offset_right);
        System.out.println("x_offset: " + DrivebaseConstants.x_offset + " y_offset: "
                + DrivebaseConstants.y_offset);
        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust]] Seeking Target");

        @SuppressWarnings("removal")
        // var resultL = camera_left.getLatestResult();
        // var resultR = camera_right.getLatestResult();
        // TODO: var resultA = Left_PI_CAM.getLatestResult(); ++
        // Right_PI_CAM.getLatestResult();
        // TODO add these ... I think
        var resultLPi = Left_PI_CAM.getLatestResult();
        var resultRPi = Right_PI_CAM.getLatestResult();

        PhotonPipelineResult result = resultRPi;
        PhotonTrackedTarget bestTarget;
        // default to R if something happens

        boolean bothTargets = resultLPi.hasTargets() && resultRPi.hasTargets();
        boolean leftTargetsNoRightTargets = resultLPi.hasTargets() && !resultRPi.hasTargets();
        boolean rightTargetsNoLeftTargets = !resultLPi.hasTargets() && resultRPi.hasTargets();
        boolean eitherTargets = resultLPi.hasTargets() || resultRPi.hasTargets();

        if (bothTargets) {
            // Both have targets, pick lower of the best of the two ambiguities.
            if (resultLPi.getBestTarget().getPoseAmbiguity() > resultRPi.getBestTarget()
                    .getPoseAmbiguity()) {
                result = resultRPi;
                bestTarget = resultRPi.getBestTarget();
            } else {
                // Favor left if equal.
                result = resultLPi;
                bestTarget = resultLPi.getBestTarget();
            }
        } else if (leftTargetsNoRightTargets) {
            result = resultLPi;
            bestTarget = resultLPi.getBestTarget();
        } else if (rightTargetsNoLeftTargets) {
            result = resultRPi;
            bestTarget = resultRPi.getBestTarget();
        } else {
            // No targets found in either
            // led.bluetoothOFF();
            driveCommand = Commands.none();
            bestTarget = null;
        }

        // PhotonPipelineResult result = resultRPi;
        // if(leftcam) result = resultLPi;

        if (eitherTargets) {
            System.out
                    .println("[LimeLightCommands/DriveBaseRotationAdjust] Target Found! Moving...");
            // bestTarget = resultRPi.getBestTarget();
            for (PhotonTrackedTarget r : result.getTargets()) {
                if (vision.getDistanceFromAprilTag(r.getFiducialId(), drivebase.getPose()) < vision
                        .getDistanceFromAprilTag(bestTarget.getFiducialId(), drivebase.getPose())) {
                    System.out.println("tag:" + r.getFiducialId());
                    bestTarget = r;
                }
            }
        }

        if (bestTarget != null) {

            int tagID = bestTarget.getFiducialId();
            // Transform2d pose = new Transform2d(drivebase.getPose().getX(),
            // drivebase.getPose().getY(), drivebase.getPose().getRotation());
            Pose2d newPose = OldVision.getAprilTagPose(tagID,
                    new Transform2d(DrivebaseConstants.x_offset,
                            DrivebaseConstants.y_offset + user_offset,
                            new Rotation2d(Math.toRadians(180))));
            System.out.println(newPose.toString());
            System.out.println("Goal Pose: " + newPose);
            driveCommand = new IndicatingCommandWrapper(drivebase.driveToPose(newPose), indicator); // ,
                                                                                                    // drivebase.getPose());
            autoAliging = 1;
        } else {
            driveCommand = Commands.none();
        }
        driveCommand.initialize();
    }

    @Override
    public void execute() {
        driveCommand.execute();
        SmartDashboard.putNumber("Commands/AutoAlign", autoAliging); // debug
    }

    /**
     * Moves the Climber Up, but at a lower speed until interrupted
     *
     * @requires AdultDiapers
     * @requires led - For visual manual control in use notification
     * @version 1.0
     */
    @Override
    public void end(boolean interrupted) {
        autoAliging = 0;
        SmartDashboard.putNumber("Commands/AutoAlign", autoAliging); // debug
        System.out.println("interupted!: " + interrupted + " Robot Pose: " + drivebase.getPose());
        // led.bluetoothOFF();
        driveCommand.end(interrupted);
    }

    /**
     * Checks if it's time to end the Command
     * 
     * @return True - End the Command False - Keep running Periodic
     */
    @Override
    public boolean isFinished() {
        return driveCommand.isFinished();
    }
}
