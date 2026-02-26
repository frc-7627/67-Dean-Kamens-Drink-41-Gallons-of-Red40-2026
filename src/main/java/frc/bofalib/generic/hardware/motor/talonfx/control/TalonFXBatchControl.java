package frc.bofalib.generic.hardware.motor.talonfx.control;

import frc.bofalib.loggable.Loggable;

// TODO: unify these into one batch control class.
public interface TalonFXBatchControl extends Loggable {
    TalonFXControl getLeaderControl();

    TalonFXControl getFollowerControl();
}
