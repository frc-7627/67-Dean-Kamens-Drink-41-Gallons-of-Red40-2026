package frc.bofalib.generic.hardware.motor.talonfx.control;

public final record TalonFXBatchRequest(
    TalonFXControlRequest request
) implements TalonFXBatchControl {
    @Override
    public TalonFXControl getFollowerControl() {
        return TalonFXControlEmpty.getInstance();
    }

    @Override
    public TalonFXControl getLeaderControl() {
        return request;
    }
}
