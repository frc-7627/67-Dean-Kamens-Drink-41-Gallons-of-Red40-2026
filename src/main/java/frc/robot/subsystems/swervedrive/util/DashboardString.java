package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardString<Subsystem> extends BaseDashboardField<Subsystem, String>
        implements DashboardField<String> {
    /**
     * A dashboard field that is only pushed.
     * 
     * @param subsystem    the subsystem.
     * @param fieldName    the name of the field.
     * @param initialValue the field's initial value.
     */
    public DashboardString(Subsystem subsystem, String fieldName, String initialValue) {
        super(subsystem, fieldName, initialValue);
    }

    /**
     * A dashboard field that is pushed and pulled.
     * 
     * @param subsystem    the subsystem.
     * @param fieldName    the name of the field.
     * @param initialValue the field's initial value.
     * @param defaultValue the field's default value.
     * @param isConstant   whether the field will be treated as a constant.
     */
    public DashboardString(Subsystem subsystem, String fieldName, String initialValue, String defaultValue,
            boolean isConstant) {
        super(subsystem, fieldName, initialValue, defaultValue, isConstant);
    }

    @Override
    public void send(String pushKey) {
        SmartDashboard.putString(pushKey, getInnerValue());
    }

    @Override
    public void recv(String pullKey, String defaultValue) {
        setInnerValue(SmartDashboard.getString(pullKey, defaultValue));
    }
}
