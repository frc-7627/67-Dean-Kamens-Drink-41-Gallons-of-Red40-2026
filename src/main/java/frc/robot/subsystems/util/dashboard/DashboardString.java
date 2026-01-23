package frc.robot.subsystems.util.dashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A dashboard field for strings.
 */
public final class DashboardString extends ObjectDashboardField<String> {
    /**
     * A pushing dashboard field for strings.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
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
     */
    public DashboardString(String subsystemName, String fieldName, String initialValue, String defaultValue) {
        super(subsystemName, fieldName, initialValue, defaultValue);
    }

    @Override
    protected final void push(String key) {
        SmartDashboard.putString(key, getInnerValue());
    }

    @Override
    protected final void pull(String key) {
        setInnerValue(SmartDashboard.getString(key, getDefaultValue()));
    }
}
