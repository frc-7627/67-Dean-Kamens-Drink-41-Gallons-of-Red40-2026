package frc.bofalib.generic.hardware.motor.sparkmax.control;

public final class SparkMaxControlDutyCycle implements SparkMaxControl {
    private double dutyCycle;

    public SparkMaxControlDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public double getDutyCycle() {
        return dutyCycle;
    }

    public void setDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }
}
