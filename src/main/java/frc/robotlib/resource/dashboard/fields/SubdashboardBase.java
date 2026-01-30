package frc.robotlib.resource.dashboard.fields;

import frc.robotlib.resource.dashboard.Subdashboard;

public abstract class SubdashboardBase implements Subdashboard {
    private final String superdashboardName;
    private final String subdashboardName;

    protected SubdashboardBase(String superdashboardName, String subdashboardName) {
        this.superdashboardName = superdashboardName;
        this.subdashboardName = subdashboardName;
    }

    @Override
    public final String getSuperdashboardName() {
        return superdashboardName;
    }

    @Override
    public final String getSubdashboardName() {
        return subdashboardName;
    }
}
