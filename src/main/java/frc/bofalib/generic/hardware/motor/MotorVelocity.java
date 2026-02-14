package frc.bofalib.generic.hardware.motor;

import java.util.function.Consumer;
import edu.wpi.first.units.measure.AngularVelocity;

public non-sealed abstract class MotorVelocity implements MotorSetting {
    private AngularVelocity angularVelocity;

    public MotorVelocity(AngularVelocity angularVelocity) {
        this.angularVelocity = angularVelocity.mutableCopy();
    }

    public final AngularVelocity getAngularVelocity() {
        return angularVelocity;
    }

    public final void setAngularVelocity(AngularVelocity angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public final void visit(
        Consumer<MotorDutyCycle> dutyCycleConsumer,
        Consumer<MotorVelocity> velocityConsumer
    ) {
        velocityConsumer.accept(this);
    }
}
