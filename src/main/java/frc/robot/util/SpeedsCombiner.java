package frc.robot.util;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.measure.AngularVelocity;

public class SpeedsCombiner {
    private final ChassisSpeeds speeds = new ChassisSpeeds();
    private final Supplier<ChassisSpeeds> translationSupplier;

    public SpeedsCombiner(Supplier<ChassisSpeeds> translationSupplier) {
        this.translationSupplier = translationSupplier;
    }

    public ChassisSpeeds getCombinedSpeeds(AngularVelocity rotation) {
        final ChassisSpeeds translation = translationSupplier.get();
        speeds.vxMetersPerSecond = translation.vxMetersPerSecond;
        speeds.vyMetersPerSecond = translation.vyMetersPerSecond;
        speeds.omegaRadiansPerSecond = rotation.in(RadiansPerSecond);
        return speeds;
    }
}
