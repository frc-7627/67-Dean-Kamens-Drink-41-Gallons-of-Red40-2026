package frc.bofalib.generic.hardware.motor;

import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.hardware.Hardware;
import frc.bofalib.loggable.Loggable;

public interface MotorHardware<
    MotorControl extends Loggable, 
    MotorConfig extends MotorConfigurator
> extends 
    Hardware<MotorControl, MotorConfig>
{
    MotorControl getSetControl(MotorSetting motorSetting);

    MotorControl getMotionControl(MotorMotion motorMotion);
}
