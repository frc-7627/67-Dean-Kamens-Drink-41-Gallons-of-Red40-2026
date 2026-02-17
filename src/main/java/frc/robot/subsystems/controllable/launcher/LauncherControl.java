package frc.robot.subsystems.controllable.launcher;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import static frc.robot.Constants.LauncherConstants.FLYWHEEL_RADIUS_FEET;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.setting.MotorVelocity;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlSetting;

public enum LauncherControl implements UniControl<LauncherImpl, TalonFXBatchControl> {
    SHOOT(impl -> impl.shootSpeedFPSSupplier);

    private final Function<LauncherImpl, TalonFXBatchControl> firstControlFunction;

    private LauncherControl(
        Function<LauncherImpl, DoubleSupplier> feetPerSecFunction
    ) {
        this.firstControlFunction = impl -> new TalonFXBatchSetting(
            new TalonFXControlSetting(
                new MotorVelocity(
                    () -> feetPerSecFunction.apply(impl).getAsDouble() / FLYWHEEL_RADIUS_FEET, 
                    RadiansPerSecond
                )
            )
        );
    }

    @Override
    public TalonFXBatchControl getFirstControl(LauncherImpl target) {
        return firstControlFunction.apply(target);
    }
}
