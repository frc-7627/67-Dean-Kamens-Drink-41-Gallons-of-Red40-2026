package frc.bofalib.generic.hardware.motor;

import frc.bofalib.generic.hardware.Hardware;

public abstract class MotorHardware<MotorControl, MotorConfig> implements 
    Hardware<MotorControl, MotorConfig>
{
    public abstract MotorControl getSetControl(MotorSetting motorSetting);
}
