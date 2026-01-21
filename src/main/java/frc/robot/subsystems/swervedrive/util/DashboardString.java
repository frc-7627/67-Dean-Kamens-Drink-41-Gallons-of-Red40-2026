package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardString<Subsystem> extends BaseDashboardField<Subsystem, String>
        implements DashboardField<String> {
    public DashboardString(Subsystem subsystem, String fieldName, String initialValue) {
        super(subsystem, fieldName, initialValue);
    }

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
