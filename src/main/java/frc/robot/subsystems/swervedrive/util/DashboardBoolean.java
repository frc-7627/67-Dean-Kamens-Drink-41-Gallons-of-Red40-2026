package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardBoolean<Subsystem> extends BaseDashboardField<Subsystem, Boolean>
        implements DashboardField<Boolean> {
    /**
     * A boolean dashboard field that is only pushed.
     * 
     * @param subsystem    the subsystem.
     * @param fieldName    the name of the field.
     * @param initialValue the field's initial value.
     */
    public DashboardBoolean(Subsystem subsystem, String fieldName, boolean initialValue) {
        super(subsystem, fieldName, initialValue);
    }

    /**
     * A boolean dashboard field that is pushed and pulled.
     * 
     * @param subsystem    the subsystem.
     * @param fieldName    the name of the field.
     * @param initialValue the field's initial and default value.
     * @param isConstant   whether the field will be treated as a constant.
     */
    public DashboardBoolean(Subsystem subsystem, String fieldName, boolean initialValue, boolean isConstant) {
        super(subsystem, fieldName, initialValue, initialValue, isConstant);
    }

    @Override
    public void send(String pushKey) {
        SmartDashboard.putBoolean(pushKey, getInnerValue());
    }

    @Override
    public void recv(String pullKey, Boolean defaultValue) {
        setInnerValue(SmartDashboard.getBoolean(pullKey, defaultValue));
    }
}
