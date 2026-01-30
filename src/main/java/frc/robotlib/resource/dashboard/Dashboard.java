package frc.robotlib.resource.dashboard;

import java.util.Collection;
import frc.robotlib.resource.Resource;

public interface Dashboard extends Resource {
    String getDashboardName();

    @Override
    default String getName() {
        return getDashboardName() + ".Dashboard";
    }

    static Dashboard create(String dashboardName,
            Collection<? extends Subdashboard> subdashboards) {
        return new DashboardImpl(dashboardName, subdashboards);
    }
}
