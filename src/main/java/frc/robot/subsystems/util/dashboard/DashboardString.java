package frc.robot.subsystems.util.dashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardString extends ObjectDashboardField<String> {
    /**
     * A string dashboard field that is only pushed.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     */
    public DashboardString(String subsystemName, String fieldName, String initialValue) {
        super(subsystemName, fieldName, initialValue);
    }

    /**
     * A string dashboard field that is pushed and pulled.
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
    protected final void send(String key) {
        SmartDashboard.putString(key, getInnerValue());
    }

    @Override
    protected final void recv(String key) {
        setInnerValue(SmartDashboard.getString(key, getDefaultValue()));
    }
}
