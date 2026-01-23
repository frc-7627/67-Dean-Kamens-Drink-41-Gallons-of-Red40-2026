package frc.robot.subsystems.util.dashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A dashboard field for strings.
 * 
 * @see DashboardField
 * @see BaseDashboardField
 * @see ObjectDashboardField
 * @see #DashboardString(String, String, String)
 * @see #DashboardString(String, String, String, String)
 */
public final class DashboardString extends ObjectDashboardField<String> {
    /**
     * A pushing dashboard field for strings.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @see ObjectDashboardField#ObjectDashboardField(String, String, String)
     */
    public DashboardString(String subsystemName, String fieldName, String initialValue) {
        super(subsystemName, fieldName, initialValue);
    }

    /**
     * A pulling dashboard field for objects.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @param defaultValue  the field's default value.
     * @see ObjectDashboardField#ObjectDashboardField(String, String, String, String)
     */
    public DashboardString(String subsystemName, String fieldName, String initialValue, String defaultValue) {
        super(subsystemName, fieldName, initialValue, defaultValue);
    }

    /**
     * {@inheritDoc}
     * 
     * @see #getInnerValue()
     * @see SmartDashboard#putString(String, String)
     */
    @Override
    protected final void push(String key) {
        SmartDashboard.putString(key, getInnerValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see #setInnerValue(String)
     * @see #getDefaultValue()
     * @see SmartDashboard#getString(String, String)
     */
    @Override
    protected final void pull(String key) {
        setInnerValue(SmartDashboard.getString(key, getDefaultValue()));
    }
}
