package frc.robot.commands.drive.direct;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import frc.robot.subsystems.drivebase.PoseSupplier;

final class RotationTarget extends OrientationTarget {
    private final Rotation2d initialOrientation;
    private final Rotation2d targetRotation;

    RotationTarget(PoseSupplier drivebase, Rotation2d targetRotation) {
        this.initialOrientation = drivebase.getPose().getRotation();
        this.targetRotation = targetRotation;
    }

    @Override
    Angle getOrientationAngle() {
        return initialOrientation.plus(targetRotation).getMeasure();
    }
}
