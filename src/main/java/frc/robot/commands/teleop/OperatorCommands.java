package frc.robot.commands.teleop;

import org.photonvision.PhotonCamera;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.Helpers.AutoAlignment;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;

/** See Constructor for details */
public class OperatorCommands {
    private final Bluetooth led;
    private final SwerveSubsystem drivebase;
    PhotonCamera camera = new PhotonCamera("Camera_Front");

    /**
    * A collection of CommandGroups, that factor in
    * Commands from different subsystems to
    * complete a single robot action,
    *
    * such as moving the elevator up, and then ejecting a
    * gamepiece in order to score a point 
    *
    */
    public OperatorCommands(Bluetooth led, SwerveSubsystem drivebase) {
        this.led      = led;
        this.drivebase = drivebase;

    }
    
    /**
     * Drive to pose, with an offset.
     * If the best target exists, drive to it, else no-op.
     * 
     * DEPRECATED
     * @return The command
     */
    public Command driveToPoseOffset() {
        System.out.println("[LimeLightCommands/DriveBaseRotationAdjust]] Seeking Target");
        @SuppressWarnings("removal")
        var result = camera.getLatestResult();
        if (result.hasTargets()) {
            System.out.println("[LimeLightCommands/DriveBaseRotationAdjust] Target Found! Moving...");

            int tagID = result.getBestTarget().getFiducialId();
            //Transform2d pose = new Transform2d(drivebase.getPose().getX(), drivebase.getPose().getY(), drivebase.getPose().getRotation());
            Pose2d new_pose = Vision.getAprilTagPose(tagID, new Transform2d(0.40, 0.2, new Rotation2d(Math.toRadians(180))));
            System.out.println(new_pose.toString());
            led.color("vomitGreen");
            return(drivebase.driveToPose(new_pose));
        }
        else {
            led.bluetoothOFF();
            return Commands.none();
        }
    }

    }
