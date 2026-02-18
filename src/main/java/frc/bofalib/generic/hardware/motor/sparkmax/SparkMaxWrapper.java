package frc.bofalib.generic.hardware.motor.sparkmax;

import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;


public interface SparkMaxWrapper extends MotorHardware<SparkMaxControl, SparkMaxConfigurator> {
    SparkMaxConfigurator getConfigurator();

    SparkMaxControl getSetControl(MotorSetting motorSetting);

}
