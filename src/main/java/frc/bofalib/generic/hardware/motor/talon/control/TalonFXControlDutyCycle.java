package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.function.Consumer;
import frc.bofalib.generic.hardware.motor.MotorDutyCycle;

public final class TalonFXControlDutyCycle extends MotorDutyCycle implements TalonFXControl {
    public TalonFXControlDutyCycle(double dutyCycle) {
        super(dutyCycle);
    }

    @Override
    public void visit(Consumer<TalonFXControlRequest> requestConsumer,
            Consumer<TalonFXControlDutyCycle> dutyCycleConsumer,
            Consumer<TalonFXControlVelocity> velocityConsumer,
            Consumer<TalonFXControlTrack> trackConsumer) {
        dutyCycleConsumer.accept(this);
    }
}
