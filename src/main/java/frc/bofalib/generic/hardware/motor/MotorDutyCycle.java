package frc.bofalib.generic.hardware.motor;

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
}
