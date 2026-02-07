package frc.bofalib.dashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

final class DashboardUtil {
    static NetworkTable getDashboardTable() {
        return NetworkTableInstance.getDefault().getTable("SmartDashboard");
    }
}
