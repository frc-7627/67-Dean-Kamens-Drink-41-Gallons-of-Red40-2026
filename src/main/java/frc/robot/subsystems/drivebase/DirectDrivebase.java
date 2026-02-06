package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Interface for the drivetrain subsystem, essentially the robot's movement
 * system and acting as the robot's body.
 */
public interface DirectDrivebase extends Subsystem, PoseSupplier {
    /**
     * Drive with the provided robot-relative speeds.
     * 
     * @param chassisSpeeds the provided robot-relative speeds.
     */
    void driveWithSpeeds(ChassisSpeeds chassisSpeeds);
}
