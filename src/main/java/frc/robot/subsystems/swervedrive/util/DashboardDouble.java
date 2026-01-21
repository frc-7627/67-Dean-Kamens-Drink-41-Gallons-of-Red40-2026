package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardDouble<Subsystem> extends BaseDashboardField<Subsystem, Double>
        implements DashboardField<Double> {
    /**
     * A double dashboard field that is only pushed.
     * 
     * @param subsystem    the subsystem.
     * @param fieldName    the name of the field.
     * @param initialValue the field's initial value.
     */
    public DashboardDouble(Subsystem subsystem, String fieldName, double initialValue) {
        super(subsystem, fieldName, initialValue);
    }

    /**
     * A double dashboard field that is pushed and pulled.
     * 
     * @param subsystem    the subsystem.
     * @param fieldName    the name of the field.
     * @param initialValue the field's initial and default value.
     * @param isConstant   whether the field will be treated as a constant.
     */
    public DashboardDouble(Subsystem subsystem, String fieldName, double initialValue,
            boolean isConstant) {
        super(subsystem, fieldName, initialValue, initialValue, isConstant);
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
