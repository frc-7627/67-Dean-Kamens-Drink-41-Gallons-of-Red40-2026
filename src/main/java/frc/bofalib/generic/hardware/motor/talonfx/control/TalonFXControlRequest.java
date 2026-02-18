package frc.bofalib.generic.hardware.motor.talonfx.control;

import java.util.function.Consumer;

import com.ctre.phoenix6.controls.ControlRequest;

public final record TalonFXControlRequest(
    ControlRequest request
) implements TalonFXControl {
    @Override
    public String getLoggableName() {
        return "Request Talon FX Control";
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
        requestConsumer.accept(this);
    }
}
