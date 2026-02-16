package frc.robot.subsystems.controllable.launcher;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import static frc.robot.Constants.LauncherConstants.FLYWHEEL_RADIUS_FEET;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.control.UniControl;
import frc.bofalib.generic.hardware.motor.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.MotorSetting;
import frc.bofalib.generic.hardware.motor.MotorVelocity;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchSetting;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControlSetting;

public enum LauncherControl implements UniControl<LauncherImpl, TalonFXBatchControl> {
    SHOOT(impl -> impl.shootSpeedFPSSupplier, MotorSetting.Type.ANGULAR_VELOCITY);

    private final Function<LauncherImpl, TalonFXBatchControl> firstControlFunction;

    private LauncherControl(
        Function<LauncherImpl, DoubleSupplier> supplierFunction,
        MotorSetting.Type type
    ) {
        final Function<LauncherImpl, MotorSetting> settingFunction = switch (type) {
            case DUTY_CYCLE -> impl -> new MotorDutyCycle(
                supplierFunction.apply(impl)
            );
            case ANGULAR_VELOCITY -> impl -> new MotorVelocity(
                () -> supplierFunction.apply(impl).getAsDouble() / FLYWHEEL_RADIUS_FEET, 
                RadiansPerSecond
            );
        };

        this.firstControlFunction = impl -> new TalonFXBatchSetting(
            new TalonFXControlSetting(
                settingFunction.apply(impl)
            )
        );
    }

    @Override
    public TalonFXBatchControl getFirstControl(LauncherImpl target) {
        return firstControlFunction.apply(target);
    }
}
