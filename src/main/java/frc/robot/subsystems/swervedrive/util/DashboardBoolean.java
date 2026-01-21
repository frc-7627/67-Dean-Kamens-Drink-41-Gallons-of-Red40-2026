package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class DashboardBoolean<Subsystem> extends BaseDashboardField<Subsystem, Boolean>
        implements DashboardField<Boolean> {
    public DashboardBoolean(Subsystem subsystem, String fieldName, boolean initialValue) {
        super(subsystem, fieldName, initialValue);
    }

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
