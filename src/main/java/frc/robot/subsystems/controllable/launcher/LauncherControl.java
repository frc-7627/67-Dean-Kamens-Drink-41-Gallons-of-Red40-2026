package frc.robot.subsystems.controllable.launcher;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.bofalib.loggable.Loggable;

public enum LauncherControl implements UniControl<LauncherImpl, TalonFXBatchControl>, Loggable {
    SHOOT("Launcher Shoot", impl -> impl.shootSpeedFPSSupplier),
    ACTIVE_IDLE("Launcher Active Idle", impl -> impl.activeIdleFPSSupplier),
    INACTIVE_IDLE("Launcher Inactive Idle", impl -> impl.inactiveIdleFPSSupplier);

    private final String name;
    private final Function<LauncherImpl, TalonFXBatchControl> firstControlFunction;

    private LauncherControl(
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
}



/*
█████████████ ███ ████ ████ █ ████ ████ ███ ██████████████ ███ ███ █ ███ ██ █ ███ ████ ███ ██ ████ ██ ██ ██ ███ ████
██ ██ █ █ ███ ██ ██ █████ ██ ██ ██ █████ ███ █ ███ █████ ███ ████████ █████ ███ ███ ██ ████ █████ ███████ ██████ ██ ███
█████ ██████ ███ ██ █████ ████ ██ ███ ██ ███ ██████ ████ ██ ████ ██ ██ ██ ████ ██ █████ ███ ███ ██ ███ ███ ██████ ██ ██
████ ██ ███ ██ ███ ███ ███ █████ ████ █████ ███ ███ ████ CTBT ███ ███ ██ ██████████ ███ ██ ███ ████ █████████ ████ ████
█████ ████ ███ ███ the ███ █████████ ███ ████ █ ██ ████████ ████ ████ █████████ ██████ ████████ ███ ████ ███ ███ ███ ██
*/