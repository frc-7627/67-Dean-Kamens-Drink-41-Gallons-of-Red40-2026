package frc.bofalib.generic.hardware.motor;

import java.util.function.Consumer;
import edu.wpi.first.units.measure.AngularVelocity;

public final class MotorVelocity implements MotorSetting {
    private AngularVelocity angularVelocity;

    public MotorVelocity(AngularVelocity angularVelocity) {
        this.angularVelocity = angularVelocity.mutableCopy();
    }

    public AngularVelocity getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(AngularVelocity angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public void visit(
        Consumer<MotorDutyCycle> dutyCycleConsumer,
        Consumer<MotorVelocity> velocityConsumer
    ) {
        velocityConsumer.accept(this);
    }
}
