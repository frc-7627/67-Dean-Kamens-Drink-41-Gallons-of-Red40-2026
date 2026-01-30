package frc.robot.resources.dashboard;

import java.util.Collection;
import java.util.List;
import frc.robotlib.resource.SharedResourceBase;

final class SharedDashboardImpl extends SharedResourceBase implements SharedDashboard {
    private final String name;
    private final List<SharedDashboardField> dashboardFields = List.of();

    SharedDashboardImpl(String dashboardName, Collection<? extends SharedDashboardField> dashboardFields) {
        super(dashboardFields);

        this.name = dashboardName;

        this.dashboardFields.addAll(dashboardFields);
    }

    @Override
    public String getDashboardName() {
        return name;
    }

    @Override
    public void periodic() {
        dashboardFields.forEach(SharedDashboardField::periodic);
    }
}
