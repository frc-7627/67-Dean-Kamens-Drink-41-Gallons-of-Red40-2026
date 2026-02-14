package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.function.Consumer;

public sealed interface TalonFXControl permits 
    TalonFXControlRequest, 
    TalonFXControlDutyCycle,
    TalonFXControlVelocity,
    TalonFXControlTrack,
    TalonFXControlEmpty
{
    void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlDutyCycle> dutyCycleConsumer,
        Consumer<TalonFXControlVelocity> velocityConsumer,
        Consumer<TalonFXControlTrack> trackConsumer
    );
}
