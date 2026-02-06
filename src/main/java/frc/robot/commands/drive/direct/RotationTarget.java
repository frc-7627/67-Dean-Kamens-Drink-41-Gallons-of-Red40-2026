package frc.robot.commands.drive.direct;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import frc.robot.subsystems.drivebase.PoseSupplier;

final class RotationTarget extends OrientationTarget {
    private final PoseSupplier drivebase;
    private final Rotation2d targetRotation;
    private Rotation2d initialOrientation = new Rotation2d();

    RotationTarget(PoseSupplier drivebase, Rotation2d targetRotation) {
        this.drivebase = drivebase;
        this.targetRotation = targetRotation;
    }

    @Override
    void reset() {
        initialOrientation = drivebase.getPose().getRotation();
    }

    @Override
    Angle getOrientationAngle() {
        return initialOrientation.plus(targetRotation).getMeasure();
    }
}
