package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardDouble extends BaseDashboardField<Double>
        implements DashboardField<Double> {
    /**
     * A double dashboard field that is only pushed.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     */
    public DashboardDouble(String subsystemName, String fieldName, double initialValue) {
        super(subsystemName, fieldName, initialValue);
    }

    /**
     * A double dashboard field that is pushed and pulled.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial and default value.
     * @param isConstant    whether the field will be treated as a constant.
     */
    public DashboardDouble(String subsystemName, String fieldName, double initialValue,
            boolean isConstant) {
        super(subsystemName, fieldName, initialValue, initialValue, isConstant);
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
