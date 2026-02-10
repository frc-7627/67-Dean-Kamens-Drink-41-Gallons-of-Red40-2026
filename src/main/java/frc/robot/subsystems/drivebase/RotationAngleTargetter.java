package frc.robot.subsystems.drivebase;

import java.util.function.DoubleSupplier;
import edu.wpi.first.math.geometry.Rotation2d;

final class RotationAngleTargetter implements AngleTargetter {
    private final DoubleSupplier orientationRadiansSupplier;
    private final double rotationRadians;

    RotationAngleTargetter(
        DoubleSupplier orientationRadiansSupplier,
        Rotation2d rotation
    ) {
        this.orientationRadiansSupplier = orientationRadiansSupplier;
        this.rotationRadians = rotation.getRadians();
    }

    @Override
    public double getTargetRadians() {
        return orientationRadiansSupplier.getAsDouble() + rotationRadians;
    }
}
