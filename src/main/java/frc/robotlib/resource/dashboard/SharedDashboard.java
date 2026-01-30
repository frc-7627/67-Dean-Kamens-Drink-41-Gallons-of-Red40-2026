package frc.robotlib.resource.dashboard;

import java.util.Collection;
import frc.robotlib.resource.SharedResource;

public interface SharedDashboard extends Dashboard, SharedResource {
    static SharedDashboard create(String dashboardName,
            Collection<? extends SharedSubdashboard> dashboardFields) {
        return new SharedDashboardImpl(dashboardName, dashboardFields);
    }
}
