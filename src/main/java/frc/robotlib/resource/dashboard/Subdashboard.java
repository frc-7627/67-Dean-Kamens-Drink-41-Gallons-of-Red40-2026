package frc.robotlib.resource.dashboard;

import frc.robotlib.resource.Subresource;

public interface Subdashboard extends Subresource {
    String getSuperdashboardName();

    String getSubdashboardName();

    default String getKeyName() {
        return getSuperdashboardName() + "/" + getSubdashboardName();
    }
}
