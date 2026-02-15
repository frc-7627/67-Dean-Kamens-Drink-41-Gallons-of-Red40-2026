package frc.bofalib.generic.hardware.motor.talon.control;

import com.ctre.phoenix6.Orchestra;

public final class TalonFXBatchEmpty implements TalonFXBatchControl {
    private static final TalonFXBatchEmpty INSTANCE = new TalonFXBatchEmpty();

    public static TalonFXBatchEmpty getInstance() {
        return INSTANCE;
    }

    private TalonFXBatchEmpty() {}

    @Override
    public TalonFXControl getLeaderControl(Orchestra orchestra) {
        return TalonFXControlEmpty.getInstance();
    }

    @Override
    public TalonFXControl getFollowerControl(Orchestra orchestra, int index) {
        return TalonFXControlEmpty.getInstance();
    }
}
