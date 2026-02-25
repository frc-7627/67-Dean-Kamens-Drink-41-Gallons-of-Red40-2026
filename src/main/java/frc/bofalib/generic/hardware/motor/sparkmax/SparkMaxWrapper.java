package frc.bofalib.generic.hardware.motor.sparkmax;

import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.query.SparkMaxQuery;
import frc.bofalib.query.DoubleQueryable;


public interface SparkMaxWrapper extends MotorHardware<SparkMaxControl, SparkMaxConfigurator>, DoubleQueryable<SparkMaxQuery> {
    SparkMaxConfigurator getConfigurator();

    SparkMaxControl getSetControl(MotorSetting motorSetting);

}
