package frc.robot.subsystems.util.dashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardDouble extends ValueDashboardField<Double> {
    public DashboardDouble(String subsystemName, String fieldName, double initialValue, FieldMode fieldMode) {
        super(subsystemName, fieldName, initialValue, fieldMode);
    }

    @Override
    protected final void send(String key) {
        SmartDashboard.putNumber(key, getInnerValue());
    }

    @Override
    protected final void recv(String key) {
        setInnerValue(SmartDashboard.getNumber(key, getDefaultValue()));
    }
}
