package frc.robot.subsystems.util.dashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A dashboard field for strings.
 * 
 * @see DashboardField
 * @see BaseDashboardField
 * @see DashboardObject
 * @see #DashboardString(String, String, String)
 * @see #DashboardString(String, String, String, String)
 */
public class DashboardString extends DashboardObject<String> {
    /**
     * A pushing dashboard field for strings.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @see DashboardObject#DashboardObject(String, String, String)
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
     * @see DashboardObject#DashboardObject(String, String, String, String)
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
    protected final void pushWithKey(String key) {
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
    protected final void pullWithKey(String key) {
        setInnerValue(SmartDashboard.getString(key, getDefaultValue()));
    }
}
