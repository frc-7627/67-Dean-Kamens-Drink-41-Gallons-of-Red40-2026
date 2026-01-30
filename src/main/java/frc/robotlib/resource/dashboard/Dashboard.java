package frc.robotlib.resource.dashboard;

import java.util.Collection;
import frc.robotlib.resource.Resource;

public interface Dashboard extends Resource {
    String getDashboardName();

    static Dashboard create(String dashboardName,
            Collection<? extends Subdashboard> dashboardFields) {
        return new DashboardImpl(dashboardName, dashboardFields);
    }
}
