package frc.bofalib.generic.hardware.motor.talonfx.control;

import java.util.function.Consumer;
import frc.bofalib.loggable.Loggable;

public sealed interface TalonFXControl extends Loggable permits 
    TalonFXControlRequest, 
    TalonFXControlSetting,
    TalonFXControlEmpty
{
    void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlSetting> settingConsumer
    );
}
