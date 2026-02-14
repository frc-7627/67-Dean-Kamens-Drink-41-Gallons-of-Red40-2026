package frc.bofalib.generic.hardware.motor;

import java.util.function.Consumer;
import java.util.function.Supplier;
import edu.wpi.first.units.measure.AngularVelocity;

public final class MotorVelocity implements MotorSetting {
    private final Supplier<AngularVelocity> angularVelocitySupplier;

    public MotorVelocity(Supplier<AngularVelocity> angularVelocitySupplier) {
        this.angularVelocitySupplier = angularVelocitySupplier;
    }

    public AngularVelocity getAngularVelocity() {
        return angularVelocitySupplier.get();
    }

    @Override
    public void visit(
        Consumer<MotorDutyCycle> dutyCycleConsumer,
        Consumer<MotorVelocity> velocityConsumer
    ) {
        velocityConsumer.accept(this);
    }
}
