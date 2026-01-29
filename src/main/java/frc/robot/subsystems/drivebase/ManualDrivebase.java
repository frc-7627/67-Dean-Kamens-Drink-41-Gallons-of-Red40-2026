package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface ManualDrivebase extends Subsystem {
    void lock();

    void zeroGyro();

    void driveWithSpeeds(ChassisSpeeds chassisSpeeds);
}
