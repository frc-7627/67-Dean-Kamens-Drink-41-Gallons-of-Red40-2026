package frc.bofalib.generic.hardware.motor;

import java.util.function.DoubleSupplier;
import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.AngularVelocityUnit;
import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.motion.MotorMotionPosition;
import frc.bofalib.generic.hardware.motor.motion.MotorMotionVelocity;
import frc.bofalib.generic.hardware.motor.setting.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.setting.MotorVelocity;
import frc.bofalib.hardware.Hardware;
import frc.bofalib.loggable.Loggable;

public interface MotorHardware<
    MotorControl extends Loggable, 
    MotorConfig extends MotorConfigurator
> extends 
    Hardware<MotorControl, MotorConfig>
{
    void zeroEncoder();

    MotorControl getSetControl(MotorSetting motorSetting);

    default MotorControl getSetDutyCycleControl(DoubleSupplier dutyCycleSupplier) {
        return getSetControl(new MotorDutyCycle(dutyCycleSupplier));
    }

    default MotorControl getSetVelocityControl(
        DoubleSupplier magnitudeSupplier, 
        AngularVelocityUnit unit
    ) {
        return getSetControl(new MotorVelocity(magnitudeSupplier, unit));
    }

    MotorControl getMotionControl(MotorMotion motorMotion);

    default MotorControl getMotionPositionControl(
        DoubleSupplier magnitudeSupplier,
        AngleUnit unit
    ) {
        return getMotionControl(new MotorMotionPosition(magnitudeSupplier, unit));
    }

    default MotorControl getMotionVelocityControl(
        DoubleSupplier magnitudeSupplier,
        AngularVelocityUnit unit
    ) {
        return getMotionControl(new MotorMotionVelocity(magnitudeSupplier, unit));
    }
}
