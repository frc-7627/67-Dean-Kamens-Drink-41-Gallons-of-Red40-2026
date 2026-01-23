package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardDouble extends BaseDashboardField<Double> {
    /**
     * A double dashboard field that is pushed and possibly pulled.
     * 
     * @param isPull        whether the field is pulled.
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     */
    public DashboardDouble(boolean isPull, String subsystemName, String fieldName, double initialValue) {
        super(isPull, subsystemName, fieldName, initialValue);
    }

    @Override
    protected void send(String pushKey) {
        SmartDashboard.putNumber(pushKey, getInnerValue());
    }

    @Override
    protected void recv(String pullKey, Double defaultValue) {
        setInnerValue(SmartDashboard.getNumber(pullKey, defaultValue));
    }
}
