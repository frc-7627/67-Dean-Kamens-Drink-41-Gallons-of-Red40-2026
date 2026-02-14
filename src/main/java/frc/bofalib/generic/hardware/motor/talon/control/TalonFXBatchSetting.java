package frc.bofalib.generic.hardware.motor.talon.control;

public record TalonFXBatchSetting(
    TalonFXControlSetting setting
) implements TalonFXBatchControl {
    @Override
    public TalonFXControl getLeaderControl() {
        return setting;
    }

    @Override
    public TalonFXControl getFollowerControl(int index) {
        return TalonFXControlEmpty.getInstance();
    }
}
