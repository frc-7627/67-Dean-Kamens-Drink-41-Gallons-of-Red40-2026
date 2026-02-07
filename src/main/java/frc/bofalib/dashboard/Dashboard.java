package frc.bofalib.dashboard;

import java.util.Collection;

public interface Dashboard extends Subdashboard {
    static Dashboard create(String dashboardName,
            Collection<? extends Subdashboard> subdashboards) {
        return new DashboardImpl(dashboardName, subdashboards);
    }
}
