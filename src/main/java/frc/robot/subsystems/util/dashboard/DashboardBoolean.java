package frc.robot.subsystems.util.dashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardBoolean extends ValueDashboardField<Boolean> {
    public DashboardBoolean(String subsystemName, String fieldName, boolean initialValue, FieldMode fieldMode) {
        super(subsystemName, fieldName, initialValue, fieldMode);
    }

    @Override
    protected final void push(String key) {
        SmartDashboard.putBoolean(key, getInnerValue());
    }

    @Override
    protected final void pull(String key) {
        setInnerValue(SmartDashboard.getBoolean(key, getDefaultValue()));
    }
}
