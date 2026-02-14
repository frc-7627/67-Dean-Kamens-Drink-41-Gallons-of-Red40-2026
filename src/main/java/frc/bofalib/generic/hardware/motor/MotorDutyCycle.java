package frc.bofalib.generic.hardware.motor;

import java.util.function.Consumer;

public final class MotorDutyCycle implements MotorSetting {
    private double dutyCycle;

    public MotorDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public double getDutyCycle() {
        return dutyCycle;
    }

    public void setDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    @Override
    public void visit(
        Consumer<MotorDutyCycle> dutyCycleConsumer,
        Consumer<MotorVelocity> velocityConsumer
    ) {
        dutyCycleConsumer.accept(this);
    }
}
