package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.function.Consumer;

import com.ctre.phoenix6.controls.ControlRequest;

public final record TalonFXControlRequest(
    ControlRequest request
) implements TalonFXControl {
    @Override
    public void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlSetting> settingConsumer
    ) {
        requestConsumer.accept(this);
    }
}
