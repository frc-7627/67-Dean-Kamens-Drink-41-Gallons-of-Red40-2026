package frc.robot.subsystems.controllable.drivebase;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface MiscDrivebase extends Subsystem {
    /**
     * Forces the robot to keep the current pose.
     */
    void lock();

    /**
     * Resets the gyro angle to zero and resets odometry to the same position, but facing toward 0
     * (red alliance station).
     */
    void zeroGyro();
}
