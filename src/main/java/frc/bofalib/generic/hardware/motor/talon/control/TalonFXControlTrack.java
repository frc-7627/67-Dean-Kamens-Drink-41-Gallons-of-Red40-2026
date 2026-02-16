package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.function.Consumer;

public final record TalonFXControlTrack() implements TalonFXControl {
    @Override
    public void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlSetting> settingConsumer,
        Consumer<TalonFXControlTrack> trackConsumer
    ) {
        trackConsumer.accept(this);
    }
}
