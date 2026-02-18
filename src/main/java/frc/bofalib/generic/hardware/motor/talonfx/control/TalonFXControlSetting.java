package frc.bofalib.generic.hardware.motor.talonfx.control;

import java.util.function.Consumer;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;

public record TalonFXControlSetting(MotorSetting setting) implements TalonFXControl {
    @Override
    public String getLoggableName() {
        return "Setting Talon FX Control";
    }

    @Override
    public String getLoggableInfo() {
        // TODO Auto-generated method stub
        return TalonFXControl.super.getLoggableInfo();
    }
    
    @Override
    public void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlSetting> settingConsumer
    ) {
        settingConsumer.accept(this);
    }
}
