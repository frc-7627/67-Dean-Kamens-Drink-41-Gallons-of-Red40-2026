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

    /**
     * Forces the robot to keep the current pose.
     */
    void lock();

    /**
     * Resets the gyro angle to zero and resets odometry to the same position, but
     * facing toward 0
     * (red alliance station).
     */
    void zeroGyro();
}
