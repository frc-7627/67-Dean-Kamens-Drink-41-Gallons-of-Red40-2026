package frc.robotlib.resource.dashboard;

import java.util.Collection;
import java.util.List;
import frc.robotlib.resource.ResourceBase;

final class DashboardImpl extends ResourceBase implements Dashboard {
    private final String dashboardName;
    private final List<Subdashboard> subdashboards = List.of();

    DashboardImpl(String dashboardName, Collection<? extends Subdashboard> subdashboards) {
        super(subdashboards);

        this.dashboardName = dashboardName;

        this.subdashboards.addAll(subdashboards);
    }

    @Override
    public String getDashboardName() {
        return dashboardName;
    }

    @Override
    public void periodic() {
        subdashboards.forEach(Subdashboard::periodic);
    }
}
