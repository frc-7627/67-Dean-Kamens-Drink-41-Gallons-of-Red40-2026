package frc.robot.resources.dashboard;

import java.util.Collection;
import frc.robotlib.resource.Resource;

public interface Dashboard extends Resource {
    String getDashboardName();

    static Dashboard create(String dashboardName, Collection<? extends DashboardField> dashboardFields) {
        return new DashboardImpl(dashboardName, dashboardFields);
    }
}
