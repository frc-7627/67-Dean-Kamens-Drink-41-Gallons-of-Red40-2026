package frc.robot.subsystems.controllable.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;

public interface DrivebaseKinematics {
    ChassisSpeeds getRobotRelativeSpeeds();

    ChassisSpeeds getFieldRelativeSpeeds();
}
