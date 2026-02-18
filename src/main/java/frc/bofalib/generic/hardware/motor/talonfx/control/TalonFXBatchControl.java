package frc.bofalib.generic.hardware.motor.talonfx.control;

import frc.bofalib.loggable.Loggable;

public interface TalonFXBatchControl extends Loggable {
    TalonFXControl getLeaderControl();

    TalonFXControl getFollowerControl();
}
