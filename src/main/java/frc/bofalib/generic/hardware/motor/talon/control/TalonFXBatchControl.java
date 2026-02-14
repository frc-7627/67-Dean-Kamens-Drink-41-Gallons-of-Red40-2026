package frc.bofalib.generic.hardware.motor.talon.control;

public interface TalonFXBatchControl {
    TalonFXControl getLeaderControl();

    TalonFXControl getFollowerControl(int index);
}
