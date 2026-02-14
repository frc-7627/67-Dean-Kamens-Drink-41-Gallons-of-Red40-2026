package frc.bofalib.generic.hardware.motor;

import java.util.function.Consumer;

public sealed interface MotorSetting permits
    MotorDutyCycle,
    MotorVelocity 
{
    void visit(
        Consumer<MotorDutyCycle> dutyCycleConsumer,
        Consumer<MotorVelocity> velocityConsumer
    );
}
