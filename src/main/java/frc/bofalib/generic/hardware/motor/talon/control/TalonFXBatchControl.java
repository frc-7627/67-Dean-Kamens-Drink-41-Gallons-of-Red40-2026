package frc.bofalib.generic.hardware.motor.talon.control;

import com.ctre.phoenix6.Orchestra;

public interface TalonFXBatchControl {
    TalonFXControl getLeaderControl(Orchestra orchestra);

    TalonFXControl getFollowerControl(Orchestra orchestra, int index);
}
