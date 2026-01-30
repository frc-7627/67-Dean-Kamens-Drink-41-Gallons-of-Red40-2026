package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface TeleopDrivebase extends Subsystem {

    /**
     * Point all modules toward the robot center, thus making the robot very difficult to move.
     * Forcing the robot to keep the current pose.
     */
    void lock();

    /**
     * Resets the gyro angle to zero and resets odometry to the same position, but facing toward 0
     * (red alliance station).
     */
    void zeroGyro();

    /**
     * Secondary method for controlling the drivebase. Given a simple {@link ChassisSpeeds} set the
     * swerve module states, to achieve the goal.
     * 
     * @param chassisSpeeds
     */
    void driveWithSpeeds(ChassisSpeeds chassisSpeeds);
}
