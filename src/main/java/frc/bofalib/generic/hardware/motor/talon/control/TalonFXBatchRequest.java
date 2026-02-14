package frc.bofalib.generic.hardware.motor.talon.control;

import com.ctre.phoenix6.Orchestra;

public final record TalonFXBatchRequest(
    TalonFXControlRequest request
) implements TalonFXBatchControl {
    @Override
    public TalonFXControl getFollowerControl(Orchestra orchestra, int index) {
        return TalonFXControlEmpty.getInstance();
    }

    @Override
    public TalonFXControl getLeaderControl(Orchestra orchestra) {
        return request;
    }
}
