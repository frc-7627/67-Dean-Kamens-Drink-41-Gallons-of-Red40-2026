package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.function.Consumer;

public sealed interface TalonFXControl permits 
    TalonFXControlRequest, 
    TalonFXControlSetting,
    TalonFXControlTrack,
    TalonFXControlEmpty
{
    void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlSetting> settingConsumer,
        Consumer<TalonFXControlTrack> trackConsumer
    );
}
