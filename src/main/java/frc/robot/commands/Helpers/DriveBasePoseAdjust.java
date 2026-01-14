package frc.robot.commands.Helpers;

import org.photonvision.PhotonCamera;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.DrivebaseConstants;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;

/** See Constructor for details */
public class DriveBasePoseAdjust extends Command {
    private SwerveSubsystem drivebase;
    PhotonCamera camera = new PhotonCamera("Camera_Front");
    Bluetooth led;

    private double user_offset;
    
    private Command driveCommand;

    /**
     * Turns the Robot to face an April Tag, using PhotonVison detections
     * 
     * @requires drivebase Swerve Drivebase provided by YAGSL
     * @requires led visually indicated when an apriltag is being detected
     * @version 2.0
     */
    public DriveBasePoseAdjust(SwerveSubsystem module, Bluetooth led, double offset) {
        this.drivebase = module;
        this.led = led;
        addRequirements(drivebase);
        addRequirements(led);

        user_offset = offset;

        // Shuffleboard!
        DrivebaseConstants.x_offset = SmartDashboard.getNumber("Vision/x_offset", DrivebaseConstants.x_offset);
        DrivebaseConstants.y_offset = SmartDashboard.getNumber("Vision/y_offset", DrivebaseConstants.y_offset);
        SmartDashboard.putNumber("Vision/x_offset", DrivebaseConstants.x_offset);
        SmartDashboard.putNumber("Vision/y_offset", DrivebaseConstants.y_offset);
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        //OperatorConstants.IntakeNoteAmps = SmartDashboard.getNumber("Intake/IntakeNote/Intake Note Amp Limit", OperatorConstants.IntakeNoteAmps);

        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Shuffleboard Updated");
    } 
    
    /** Run once at Command Start */
    @Override
    public void initialize() {
        DrivebaseConstants.x_offset = SmartDashboard.getNumber("Vision/x_offset", DrivebaseConstants.x_offset);
        DrivebaseConstants.y_offset = SmartDashboard.getNumber("Vision/y_offset", DrivebaseConstants.y_offset);
        System.out.println("x_offset: " + DrivebaseConstants.x_offset + " y_offset: " + DrivebaseConstants.y_offset);
        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust]] Seeking Target");
        @SuppressWarnings("removal")
        var result = camera.getLatestResult();
        if (result.hasTargets()) {
            System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Target Found! Moving...");

            int tagID = result.getBestTarget().getFiducialId();
            //Transform2d pose = new Transform2d(drivebase.getPose().getX(), drivebase.getPose().getY(), drivebase.getPose().getRotation());
            Pose2d new_pose = Vision.getAprilTagPose(tagID, new Transform2d(DrivebaseConstants.x_offset, DrivebaseConstants.y_offset + user_offset, new Rotation2d(Math.toRadians(180))));
            System.out.println(new_pose.toString());
            led.color("vomitGreen");
            driveCommand = drivebase.driveToPose(new_pose);
        }
        else {
            led.bluetoothOFF();
            driveCommand = Commands.none();
        }

        driveCommand.schedule();
    }

    @Override
    public void execute() {
    }


     /** 
      * Run once at Command End 
      * 
      * @param interupted - False if Command ended by isFinished() 
      *                     True if by something else like 
      *                              letting go of a button
      */
    @Override
    public void end(boolean interrupted) {
        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Interupted");
        driveCommand.cancel();
        driveCommand.end(true);
        drivebase.driveToDistanceCommand(0.0, 0.0).schedule();
    }

    /** 
      * Checks if it's time to end the Command 
      * 
      * @return True - End the Command
      *         False - Keep running Periodic
      */
    @Override 
    public boolean isFinished() {
        return false;
    }
}
