package frc.robotlib.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import frc.robotlib.resource.SharedResourceBase;

final class SharedDashboardImpl extends SharedResourceBase implements SharedDashboard {
    private final String dashboardName;
    private final List<SharedSubdashboard> dashboardFields = new ArrayList<>();

    SharedDashboardImpl(String dashboardName,
            Collection<? extends SharedSubdashboard> dashboardFields) {
        super(dashboardFields);

        this.dashboardName = dashboardName;

        this.dashboardFields.addAll(dashboardFields);
    }

     @Override
    public Optional<String> getDashboardName() {
        return Optional.of(dashboardName);
    }

    @Override
    public Optional<String> getSuperdashboardName() {
        return Optional.empty();
    }

    @Override
    public void periodic() {
        dashboardFields.forEach(SharedSubdashboard::periodic);
    }
}
