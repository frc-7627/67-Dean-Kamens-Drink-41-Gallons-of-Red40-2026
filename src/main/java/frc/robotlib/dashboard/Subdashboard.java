package frc.robotlib.dashboard;

import java.util.Optional;

public interface Subdashboard {
    Optional<String> getSuperdashboardName();

    Optional<String> getDashboardName();

    default String getKeyName() {
        final Optional<String> superdashboardNameOptional = getSuperdashboardName();
        final Optional<String> dashboardNameOptional = getDashboardName();

        if (superdashboardNameOptional.isPresent()) {
            if (dashboardNameOptional.isPresent()) {
                return superdashboardNameOptional.get() + "/" + dashboardNameOptional.get();
            } else {
                return superdashboardNameOptional.get();
            }
        } else {
            if (dashboardNameOptional.isPresent()) {
                return dashboardNameOptional.get();
            } else {
                throw new AssertionError("Superdashboard or subdashboard needs name");
            }
        }
    }
}
