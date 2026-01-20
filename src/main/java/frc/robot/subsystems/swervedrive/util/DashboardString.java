package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public record DashboardString(String value) implements DashboardValue<DashboardString> {
    @Override
    public DashboardString recv(String key) {
        return new DashboardString(SmartDashboard.getString(key, value));
    }

    @Override
    public void send(String key) {
        SmartDashboard.putString(key, value);
    }
}
