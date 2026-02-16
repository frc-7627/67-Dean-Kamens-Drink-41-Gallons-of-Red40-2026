package frc.bofalib.generic.hardware.motor.talonfx.control;

public record TalonFXBatchSetting(
    TalonFXControlSetting setting
) implements TalonFXBatchControl {
    @Override
    public TalonFXControl getLeaderControl() {
        return setting;
    }

    @Override
    public TalonFXControl getFollowerControl() {
        return TalonFXControlEmpty.getInstance();
    }
}
