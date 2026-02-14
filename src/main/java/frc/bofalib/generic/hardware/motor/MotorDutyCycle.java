package frc.bofalib.generic.hardware.motor;

import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

public final class MotorDutyCycle implements MotorSetting {
    private final DoubleSupplier dutyCycleSupplier;

    public MotorDutyCycle(DoubleSupplier dutyCycleSupplier) {
        this.dutyCycleSupplier = dutyCycleSupplier;
    }

    public double getDutyCycle() {
        return dutyCycleSupplier.getAsDouble();
    }

    @Override
    public final void visit(
        Consumer<MotorDutyCycle> dutyCycleConsumer,
        Consumer<MotorVelocity> velocityConsumer
    ) {
        dutyCycleConsumer.accept(this);
    }
}
