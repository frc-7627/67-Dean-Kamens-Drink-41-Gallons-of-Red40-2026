package frc.bofalib.generic.hardware.motor.sparkmax.control;

import frc.bofalib.generic.hardware.motor.DutyCycle;

public final class SparkMaxControlDutyCycle extends DutyCycle implements SparkMaxControl {
    public SparkMaxControlDutyCycle(double dutyCycle) {
        super(dutyCycle);
    }
}
