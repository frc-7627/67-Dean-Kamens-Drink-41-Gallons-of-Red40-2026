package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardString extends BaseDashboardField<String>
        implements DashboardField<String> {
    /**
     * A dashboard field that is only pushed.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     */
    public DashboardString(String subsystemName, String fieldName, String initialValue) {
        super(subsystemName, fieldName, initialValue);
    }

    /**
     * A dashboard field that is pushed and pulled.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @param defaultValue  the field's default value.
     * @param isConstant    whether the field will be treated as a constant.
     */
    public DashboardString(String subsystemName, String fieldName, String initialValue, String defaultValue,
            boolean isConstant) {
        super(subsystemName, fieldName, initialValue, defaultValue, isConstant);
    }

    @Override
    protected void send(String pushKey) {
        SmartDashboard.putString(pushKey, getInnerValue());
    }

    @Override
    protected void recv(String pullKey, String defaultValue) {
        setInnerValue(SmartDashboard.getString(pullKey, defaultValue));
    }
}
