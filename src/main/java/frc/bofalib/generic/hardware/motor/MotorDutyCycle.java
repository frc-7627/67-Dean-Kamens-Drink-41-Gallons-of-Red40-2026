package frc.bofalib.generic.hardware.motor;

import java.util.function.Consumer;

public non-sealed abstract class MotorDutyCycle implements MotorSetting {
    private double dutyCycle;

    public MotorDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public final double getDutyCycle() {
        return dutyCycle;
    }

    public final void setDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    @Override
    public final void visit(
        Consumer<MotorDutyCycle> dutyCycleConsumer,
        Consumer<MotorVelocity> velocityConsumer
    ) {
        dutyCycleConsumer.accept(this);
    }
}
