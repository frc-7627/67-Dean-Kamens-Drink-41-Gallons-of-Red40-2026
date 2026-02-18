package frc.bofalib.generic.hardware.motor.talonfx.control;

public final record TalonFXBatchRequest(
    TalonFXControlRequest request
) implements TalonFXBatchControl {
    @Override
    public String getLoggableName() {
        return "Request Batch Talon FX Control";
    }

    @Override
    public String getLoggableInfo() {
        // TODO Auto-generated method stub
        return TalonFXBatchControl.super.getLoggableInfo();
    }

    @Override
    public TalonFXControl getFollowerControl() {
        return TalonFXControlEmpty.getInstance();
    }

    @Override
    public TalonFXControl getLeaderControl() {
        return request;
    }
}
