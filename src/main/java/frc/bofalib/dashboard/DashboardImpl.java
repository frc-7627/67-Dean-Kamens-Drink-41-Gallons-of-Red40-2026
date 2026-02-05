package frc.bofalib.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import frc.bofalib.resource.ResourceBase;

final class DashboardImpl implements Dashboard {
    private final String dashboardName;
    private final List<Subdashboard> subdashboards = new ArrayList<>();

    DashboardImpl(String dashboardName, Collection<? extends Subdashboard> subdashboards) {
        this.dashboardName = dashboardName;

        this.subdashboards.addAll(subdashboards);
    }

    @Override
    public Optional<String> getDashboardName() {
        return Optional.of(dashboardName);
    }

    @Override
    public Optional<String> getSuperdashboardName() {
        return Optional.empty();
    }
}
