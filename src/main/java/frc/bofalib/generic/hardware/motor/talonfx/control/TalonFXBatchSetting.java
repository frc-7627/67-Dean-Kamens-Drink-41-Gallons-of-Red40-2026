package frc.bofalib.generic.hardware.motor.talonfx.control;

public record TalonFXBatchSetting(
    TalonFXControlSetting setting
) implements TalonFXBatchControl {
    @Override
    public String getLoggableName() {
        return "Setting Batch Talon FX Control";
    }

    @Override
    public String getLoggableInfo() {
        // TODO Auto-generated method stub
        return TalonFXBatchControl.super.getLoggableInfo();
    }

    @Override
    public TalonFXControl getLeaderControl() {
        return setting;
    }

    @Override
    public TalonFXControl getFollowerControl() {
        return TalonFXControlEmpty.getInstance();
    }
}
