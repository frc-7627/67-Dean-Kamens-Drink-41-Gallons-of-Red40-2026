package frc.bofalib.generic.hardware.motor.talonfx.control;

import java.util.function.Consumer;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;

public record TalonFXControlSetting(MotorSetting setting) implements TalonFXControl {
    @Override
    public void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlSetting> settingConsumer
    ) {
        settingConsumer.accept(this);
    }
}
