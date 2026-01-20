package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public record DashboardBoolean(boolean value) implements DashboardValue<DashboardBoolean> {
    @Override
    public DashboardBoolean recv(String key) {
        return new DashboardBoolean(SmartDashboard.getBoolean(key, value));
    }

    @Override
    public void send(String key) {
        SmartDashboard.putBoolean(key, value);
    }
}
