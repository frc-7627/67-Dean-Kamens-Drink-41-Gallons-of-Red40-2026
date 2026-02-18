package frc.bofalib.generic.hardware.motor.talonfx.control;

public final class TalonFXBatchEmpty implements TalonFXBatchControl {
    private static final TalonFXBatchEmpty INSTANCE = new TalonFXBatchEmpty();

    public static TalonFXBatchEmpty getInstance() {
        return INSTANCE;
    }

    private TalonFXBatchEmpty() {}

    @Override
    public String getLoggableName() {
        return "Empty Batch Talon FX Control";
    }

    @Override
    public TalonFXControl getLeaderControl() {
        return TalonFXControlEmpty.getInstance();
    }

    @Override
    public TalonFXControl getFollowerControl() {
        return TalonFXControlEmpty.getInstance();
    }
}
