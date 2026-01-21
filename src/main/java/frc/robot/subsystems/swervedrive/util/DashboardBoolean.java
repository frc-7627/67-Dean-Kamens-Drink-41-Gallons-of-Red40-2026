package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardBoolean extends BaseDashboardField<Boolean>
        implements DashboardField<Boolean> {
    /**
     * A boolean dashboard field that is only pushed.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     */
    public DashboardBoolean(String subsystemName, String fieldName, boolean initialValue) {
        super(subsystemName, fieldName, initialValue);
    }

    /**
     * A boolean dashboard field that is pushed and pulled.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial and default value.
     * @param isConstant    whether the field will be treated as a constant.
     */
    public DashboardBoolean(String subsystemName, String fieldName, boolean initialValue, boolean isConstant) {
        super(subsystemName, fieldName, initialValue, initialValue, isConstant);
    }

    @Override
    protected void send(String pushKey) {
        SmartDashboard.putBoolean(pushKey, getInnerValue());
    }

    @Override
    protected void recv(String pullKey, Boolean defaultValue) {
        setInnerValue(SmartDashboard.getBoolean(pullKey, defaultValue));
    }
}
