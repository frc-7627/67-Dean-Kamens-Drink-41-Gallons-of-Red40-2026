package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardDouble<Subsystem> extends BaseDashboardField<Subsystem, Double>
        implements DashboardField<Double> {
    public DashboardDouble(Subsystem subsystem, String fieldName, double inner) {
        super(subsystem, fieldName, inner);
    }

    public DashboardDouble(Subsystem subsystem, String fieldName, double inner,
            boolean isConstant) {
        super(subsystem, fieldName, inner, inner, isConstant);
    }

    @Override
    public void send(String pushKey) {
        SmartDashboard.putNumber(pushKey, getInnerValue());
    }

    @Override
    public void recv(String pullKey, Double defaultValue) {
        setInnerValue(SmartDashboard.getNumber(pullKey, defaultValue));
    }
}
