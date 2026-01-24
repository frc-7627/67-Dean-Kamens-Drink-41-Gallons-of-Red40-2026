package frc.robot.subsystems.util.dashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A dashboard field for booleans.
 * 
 * @see DashboardField
 * @see BaseDashboardField
 * @see DashboardValue
 * @see #DashboardBoolean(String, String, Boolean, FieldMode)
 */
public final class DashboardBoolean extends DashboardValue<Boolean> {
    /**
     * A pushing or pulling dashboard field for booleans.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @param fieldMode     whether the field is pushing or pulling.
     * @see DashboardValue#DashboardValue(String, String, Boolean, FieldMode)
     */
    public DashboardBoolean(String subsystemName, String fieldName, boolean initialValue, FieldMode fieldMode) {
        super(subsystemName, fieldName, initialValue, fieldMode);
    }

    /**
     * {@inheritDoc}
     * 
     * @see #getInnerValue()
     * @see SmartDashboard#putBoolean(String, Boolean)
     */
    @Override
    protected final void pushWithKey(String key) {
        SmartDashboard.putBoolean(key, getInnerValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see #setInnerValue(Boolean)
     * @see #getDefaultValue()
     * @see SmartDashboard#getBoolean(String, Boolean)
     */
    @Override
    protected final void pullWithKey(String key) {
        setInnerValue(SmartDashboard.getBoolean(key, getDefaultValue()));
    }
}
