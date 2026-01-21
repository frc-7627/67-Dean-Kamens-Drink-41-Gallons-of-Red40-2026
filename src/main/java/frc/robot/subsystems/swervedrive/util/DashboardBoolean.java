package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardBoolean<Subsystem> extends BaseDashboardField<Subsystem, Boolean>
        implements DashboardField<Boolean> {
    public DashboardBoolean(Subsystem subsystem, String fieldName, boolean inner) {
        super(subsystem, fieldName, inner);
    }

    public DashboardBoolean(Subsystem subsystem, String fieldName, boolean inner, boolean isConstant) {
        super(subsystem, fieldName, inner, inner, isConstant);
    }

    @Override
    public void send(String key) {
        SmartDashboard.putBoolean(key, getInner());
    }

    @Override
    public void recv(String key, Boolean defaultValue) {
        setInner(SmartDashboard.getBoolean(key, defaultValue));
    }
}
