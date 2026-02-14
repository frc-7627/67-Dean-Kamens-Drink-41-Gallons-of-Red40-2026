package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.function.Consumer;
import frc.bofalib.generic.hardware.motor.MotorSetting;

public record TalonFXControlSetting(MotorSetting setting) implements TalonFXControl {
    @Override
    public void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlSetting> settingConsumer,
        Consumer<TalonFXControlTrack> trackConsumer
    ) {
        settingConsumer.accept(this);
    }
}
