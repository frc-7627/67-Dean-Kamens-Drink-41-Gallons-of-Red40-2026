package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface FieldRelativeDrivebase extends Subsystem {
    void driveWithFieldRelativeSpeeds(ChassisSpeeds fieldRelativeSpeeds);
}
