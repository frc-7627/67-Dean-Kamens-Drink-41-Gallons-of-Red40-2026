package frc.robot.subsystems.controllable.launcher;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import java.util.Optional;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;

public enum LauncherControlSimple implements LauncherControl {
    SHOOT_MANUAL("Launcher Shoot Manual", impl -> impl.shootSpeedFPSSupplier),
    ACTIVE_IDLE("Launcher Active Idle", impl -> impl.activeIdleFPSSupplier),
    INACTIVE_IDLE("Launcher Inactive Idle", impl -> impl.inactiveIdleFPSSupplier);

    private final String name;
    private final Function<LauncherImpl, TalonFXBatchControl> firstControlFunction;

    private LauncherControlSimple(
        String name,
        Function<LauncherImpl, DoubleSupplier> feetPerSecFunction
    ) {
        this.name = name;
        this.firstControlFunction = impl -> impl.motors.getSetVelocityControl(
            () -> Launcher.toAngularVelocityRPS(feetPerSecFunction.apply(impl).getAsDouble()), 
            RotationsPerSecond
        );
    }

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public TalonFXBatchControl getFirstControl(LauncherImpl target) {
        return firstControlFunction.apply(target);
    }

    @Override
    public Optional<DoubleSupplier> getTargetRPSSupplier(LauncherImpl impl) {
        return equals(SHOOT_MANUAL) 
            ? Optional.of(() -> Launcher.toAngularVelocityRPS(
                impl.shootSpeedFPSSupplier.getAsDouble()
            )) 
            : Optional.empty();
    }
}



/*
█████████████ ███ ████ ████ █ ████ ████ ███ ██████████████ ███ ███ █ ███ ██ █ ███ ████ ███ ██ ████ ██ ██ ██ ███ ████
██ ██ █ █ ███ ██ ██ █████ ██ ██ ██ █████ ███ █ ███ █████ ███ ████████ █████ ███ ███ ██ ████ █████ ███████ ██████ ██ ███
█████ ██████ ███ ██ █████ ████ ██ ███ ██ ███ ██████ ████ ██ ████ ██ ██ ██ ████ ██ █████ ███ ███ ██ ███ ███ ██████ ██ ██
████ ██ ███ ██ ███ ███ ███ █████ ████ █████ ███ ███ ████ CTBT ███ ███ ██ ██████████ ███ ██ ███ ████ █████████ ████ ████
█████ ████ ███ ███ the ███ █████████ ███ ████ █ ██ ████████ ████ ████ █████████ ██████ ████████ ███ ████ ███ ███ ███ ██
*/