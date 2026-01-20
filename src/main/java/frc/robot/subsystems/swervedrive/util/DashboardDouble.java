package frc.robot.subsystems.swervedrive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public record DashboardDouble(double value) implements DashboardValue<DashboardDouble> {
    @Override
    public DashboardDouble recv(String key) {
        return new DashboardDouble(SmartDashboard.getNumber(key, value));
    }

    @Override
    public void send(String key) {
        SmartDashboard.putNumber(key, value);
    }
}
