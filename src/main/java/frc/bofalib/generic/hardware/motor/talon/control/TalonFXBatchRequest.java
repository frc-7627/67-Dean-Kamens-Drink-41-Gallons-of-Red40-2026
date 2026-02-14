package frc.bofalib.generic.hardware.motor.talon.control;

public final record TalonFXBatchRequest(
    TalonFXControlRequest request
) implements TalonFXBatchControl {
    @Override
    public TalonFXControl getFollowerControl(int index) {
        return TalonFXControlEmpty.getInstance();
    }

    @Override
    public TalonFXControl getLeaderControl() {
        return request;
    }
}
