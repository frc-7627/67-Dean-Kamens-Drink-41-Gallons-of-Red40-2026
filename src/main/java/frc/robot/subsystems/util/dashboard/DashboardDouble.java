package frc.robot.subsystems.util.dashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardDouble extends ValueDashboardField<Double> {
    /**
     * A pushing or pulling dashboard field for doubles.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @param fieldMode     whether the field is pushing or pulling.
     */
    public DashboardDouble(String subsystemName, String fieldName, double initialValue, FieldMode fieldMode) {
        super(subsystemName, fieldName, initialValue, fieldMode);
    }

    @Override
    protected final void push(String key) {
        SmartDashboard.putNumber(key, getInnerValue());
    }

    @Override
    protected final void pull(String key) {
        setInnerValue(SmartDashboard.getNumber(key, getDefaultValue()));
    }
}
