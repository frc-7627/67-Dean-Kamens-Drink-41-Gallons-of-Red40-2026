package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardBoolean extends BaseDashboardField<Boolean>
        implements DashboardField<Boolean> {
    /**
     * A boolean dashboard field that is pushed and possibly pulled.
     * 
     * @param isPull        whether the field is pulled.
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     */
    public DashboardBoolean(boolean isPull, String subsystemName, String fieldName, boolean initialValue) {
        super(isPull, subsystemName, fieldName, initialValue);
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
