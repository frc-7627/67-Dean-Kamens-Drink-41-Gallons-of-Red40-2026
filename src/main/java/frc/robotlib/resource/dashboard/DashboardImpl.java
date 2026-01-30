package frc.robotlib.resource.dashboard;

import java.util.Collection;
import java.util.List;
import frc.robotlib.resource.ResourceBase;

final class DashboardImpl extends ResourceBase implements Dashboard {
    private final String dashboardName;
    private final List<DashboardField> dashboardFields = List.of();

    DashboardImpl(String dashboardName, Collection<? extends DashboardField> dashboardFields) {
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
        dashboardFields.forEach(DashboardField::periodic);
    }
}
