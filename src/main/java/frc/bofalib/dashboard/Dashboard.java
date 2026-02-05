package frc.bofalib.dashboard;

import java.util.Collection;
import frc.bofalib.resource.Resource;

public interface Dashboard extends Resource, Subdashboard {
    @Override
    default String getName() {
        return getDashboardName() + ".Dashboard";
    }

    static Dashboard create(String dashboardName,
            Collection<? extends Subdashboard> subdashboards) {
        return new DashboardImpl(dashboardName, subdashboards);
    }
}
