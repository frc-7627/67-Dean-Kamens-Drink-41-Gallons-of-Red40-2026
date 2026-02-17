package frc.bofalib.generic.hardware.motor.talonfx;

import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXGroupQuery;
import frc.bofalib.music.Instrument;
import frc.bofalib.query.DoubleQueryable;

public interface TalonFXGroup extends
    MotorHardware<TalonFXBatchControl, TalonFXCommonConfigurator>,
    DoubleQueryable<TalonFXGroupQuery>,
    Instrument 
{}
