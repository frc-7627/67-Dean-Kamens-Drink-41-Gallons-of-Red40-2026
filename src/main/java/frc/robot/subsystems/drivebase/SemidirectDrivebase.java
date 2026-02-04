package frc.robot.subsystems.drivebase;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public interface SemidirectDrivebase extends DirectDrivebase, KinematicSupplier {
    AngularControl getAngularControl();
}
