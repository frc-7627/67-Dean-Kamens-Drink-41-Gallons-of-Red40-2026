package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.function.Consumer;

public sealed interface TalonFXControl permits 
    TalonFXControlRequest, 
    TalonFXControlSetting,
    TalonFXControlEmpty
{
    void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlSetting> settingConsumer
    );
}
