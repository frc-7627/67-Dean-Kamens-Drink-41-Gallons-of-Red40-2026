package frc.bofalib.generic.hardware.motor;

public abstract class DutyCycle {
    private double dutyCycle;

    public DutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public final double getDutyCycle() {
        return dutyCycle;
    }

    public final void setDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }
}
