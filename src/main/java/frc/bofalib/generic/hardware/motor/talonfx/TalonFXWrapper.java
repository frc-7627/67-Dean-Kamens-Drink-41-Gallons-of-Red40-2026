package frc.bofalib.generic.hardware.motor.talonfx;

import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXQuery;
import frc.bofalib.music.Instrument;
import frc.bofalib.query.DoubleQueryable;

public interface TalonFXWrapper extends
    MotorHardware<TalonFXControl, TalonFXCommonConfigurator>,
    DoubleQueryable<TalonFXQuery>,
    Instrument
{
    
}
