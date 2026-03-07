package frc.robot.subsystems.controllable.launcher;

import static edu.wpi.first.units.Units.Feet;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import java.util.Optional;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import static frc.robot.Constants.LauncherConstants.*;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.robot.subsystems.controllable.drivebase.DistanceTargetter;

public final class LauncherControlVarShoot implements LauncherControl {
    private final Function<LauncherImpl, DoubleSupplier> rpsFunction;

    public LauncherControlVarShoot(DistanceTargetter targetter) {
        this.rpsFunction = impl -> () -> Launcher.toAngularVelocityRPS(
            DISTANCE_FEET_TO_MOTOR_FPS_MAP.get(
                Feet.convertFrom(targetter.getTargetMeters(), Meters)
            )
        );
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
