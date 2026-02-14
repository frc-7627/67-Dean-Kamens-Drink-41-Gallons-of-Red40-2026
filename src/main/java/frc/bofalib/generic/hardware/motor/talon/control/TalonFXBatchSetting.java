package frc.bofalib.generic.hardware.motor.talon.control;

import com.ctre.phoenix6.Orchestra;

public record TalonFXBatchSetting(
    TalonFXControlSetting setting
) implements TalonFXBatchControl {
    @Override
    public TalonFXControl getLeaderControl(Orchestra orchestra) {
        return setting;
    }

    @Override
    public TalonFXControl getFollowerControl(Orchestra orchestra, int index) {
        return TalonFXControlEmpty.getInstance();
    }
}
