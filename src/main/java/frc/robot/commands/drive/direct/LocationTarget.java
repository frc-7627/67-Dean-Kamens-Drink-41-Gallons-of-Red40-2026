package frc.robot.commands.drive.direct;

import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import frc.robot.subsystems.drivebase.PoseSupplier;

final class LocationTarget extends OrientationTarget {
    private final Supplier<Translation2d> currentLocationSupplier;
    private final Translation2d targetLocation;

    LocationTarget(
        PoseSupplier drivebase,
        Translation2d targetLocation
    ) {
        this.currentLocationSupplier = () -> drivebase.getPose().getTranslation();
        this.targetLocation = targetLocation;
    }

    @Override
    Angle getOrientationAngle() {
        return targetLocation
            .minus(currentLocationSupplier.get())
            .getAngle().getMeasure();
    }
}
