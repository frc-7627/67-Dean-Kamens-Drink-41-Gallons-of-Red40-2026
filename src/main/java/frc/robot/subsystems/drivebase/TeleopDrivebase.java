package frc.robot.subsystems.drivebase;

public interface TeleopDrivebase extends SemidirectDrivebase {
    /**
     * Resets the gyro angle to zero and resets odometry to the same position, but facing toward 0
     * (red alliance station).
     */
    void zeroGyro();
}
