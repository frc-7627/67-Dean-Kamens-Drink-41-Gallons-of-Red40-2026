package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.function.Consumer;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.bofalib.generic.hardware.motor.MotorVelocity;

public final class TalonFXControlVelocity extends MotorVelocity implements TalonFXControl {
    public TalonFXControlVelocity(AngularVelocity angularVelocity) {
        super(angularVelocity);
    }

    @Override
    public void visit(
        Consumer<TalonFXControlRequest> requestConsumer,
        Consumer<TalonFXControlDutyCycle> dutyCycleConsumer,
        Consumer<TalonFXControlVelocity> velocityConsumer,
        Consumer<TalonFXControlTrack> trackConsumer
    ) {
        velocityConsumer.accept(this);
    }
}
