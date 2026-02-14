package frc.bofalib.generic.hardware.motor.sparkmax.control;

import frc.bofalib.generic.hardware.motor.MotorDutyCycle;

public final class SparkMaxControlDutyCycle extends MotorDutyCycle implements SparkMaxControl {
    public SparkMaxControlDutyCycle(double dutyCycle) {
        super(dutyCycle);
    }
}
