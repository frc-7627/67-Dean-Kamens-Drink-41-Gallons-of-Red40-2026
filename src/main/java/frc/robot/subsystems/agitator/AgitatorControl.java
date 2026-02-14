package frc.robot.subsystems.agitator;

import frc.bofalib.control.UniControl;
import frc.bofalib.generic.hardware.motor.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlSetting;
import static frc.robot.Constants.AgitatorConstants.*;

public enum AgitatorControl implements UniControl<SparkMaxControl> {
    AWAY(AGITATOR_SPEED),
    AWAY_MANUAL(MANUAL_AGITATOR_SPEED),
    TOWARD(-AGITATOR_SPEED),
    TOWARD_MANUAL(-MANUAL_AGITATOR_SPEED);

    private final SparkMaxControl sparkMaxControl;

    private AgitatorControl(double dutyCycle) {
        this.sparkMaxControl = new SparkMaxControlSetting(new MotorDutyCycle(dutyCycle));
    }

    @Override
    public SparkMaxControl getFirstControl() {
        return sparkMaxControl;
    }
}
