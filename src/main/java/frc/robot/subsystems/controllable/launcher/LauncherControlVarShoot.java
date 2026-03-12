package frc.robot.subsystems.controllable.launcher;

import static edu.wpi.first.units.Units.Feet;
import static edu.wpi.first.units.Units.FeetPerSecond;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import java.util.Optional;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.robot.subsystems.controllable.drivebase.DistanceTargetter;
import frc.robot.subsystems.controllable.drivebase.DrivebaseKinematics;

public final class LauncherControlVarShoot implements LauncherControl {
    private final Function<LauncherImpl, DoubleSupplier> rpsFunction;

    public LauncherControlVarShoot(DistanceTargetter targetter, LauncherDomain domain) {
        this.rpsFunction = impl -> () -> Launcher.toAngularVelocityRPS(
            getShootVelocityFPS(impl.kinematics, targetter, domain)
        );
    }

    private double getShootVelocityFPS(
        DrivebaseKinematics kinematics, 
        DistanceTargetter targetter, 
        LauncherDomain domain
    ) {
        final double distanceFeet = Feet.convertFrom(targetter.getTargetMeters(), Meters);

        final double robotRelativeYVelocityFPS = FeetPerSecond.convertFrom(
            kinematics.getRobotRelativeSpeeds().vxMetersPerSecond,
            MetersPerSecond
        );

        final double baseShootVelocityFPS = domain.distanceFeetToMotorFPSMap.get(
            distanceFeet
        );
        
        return baseShootVelocityFPS - robotRelativeYVelocityFPS;
    }

    @Override
    public String getLoggableName() {
        return "Launcher Shoot Variable";
    }

    @Override
    public TalonFXBatchControl getFirstControl(LauncherImpl target) {
        return target.motors.getSetVelocityControl(
            rpsFunction.apply(target), 
            RotationsPerSecond
        );
    }

    @Override
    public Optional<DoubleSupplier> getTargetRPSSupplier(LauncherImpl impl) {
        return Optional.of(rpsFunction.apply(impl));
    }
}
