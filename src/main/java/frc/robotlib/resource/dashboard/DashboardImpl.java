package frc.robotlib.resource.dashboard;

import java.util.Collection;
import java.util.List;
import frc.robotlib.resource.ResourceBase;

final class DashboardImpl extends ResourceBase implements Dashboard {
    private final String dashboardName;
    private final List<Subdashboard> dashboardFields = List.of();

    DashboardImpl(String dashboardName, Collection<? extends Subdashboard> dashboardFields) {
        super(dashboardFields);

        this.dashboardName = dashboardName;

        this.dashboardFields.addAll(dashboardFields);
    }

    @Override
    public String getDashboardName() {
        return dashboardName;
    }

    @Override
    public void periodic() {
        dashboardFields.forEach(Subdashboard::periodic);
    }
}
