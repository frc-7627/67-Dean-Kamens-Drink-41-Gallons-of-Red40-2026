package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;

public interface KinematicSupplier extends PoseSupplier {
    public ChassisSpeeds getSpeeds();
}
