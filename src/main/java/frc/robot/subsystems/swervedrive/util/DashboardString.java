package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardString<Subsystem> extends BaseDashboardField<Subsystem, String>
        implements DashboardField<String> {
    public DashboardString(Subsystem subsystem, String fieldName, String inner) {
        super(subsystem, fieldName, inner);
    }

    public DashboardString(Subsystem subsystem, String fieldName, String inner, String defaultValue,
            boolean isConstant) {
        super(subsystem, fieldName, inner, defaultValue, isConstant);
    }

    @Override
    public void send(String pushKey) {
        SmartDashboard.putString(pushKey, getInner());
    }

    @Override
    public void recv(String pullKey, String defaultValue) {
        setInner(SmartDashboard.getString(pullKey, defaultValue));
    }
}
