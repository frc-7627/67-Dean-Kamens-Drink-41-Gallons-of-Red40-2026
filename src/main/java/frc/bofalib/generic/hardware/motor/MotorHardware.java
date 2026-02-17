package frc.bofalib.generic.hardware.motor;

import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.hardware.Hardware;

public interface MotorHardware<MotorControl, MotorConfig extends MotorConfigurator> extends 
    Hardware<MotorControl, MotorConfig>
{
    MotorControl getSetControl(MotorSetting motorSetting);
}
