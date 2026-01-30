package frc.robotlib.resource.dashboard.fields;

import frc.robotlib.resource.dashboard.Subdashboard;

abstract class DashboardFieldBase implements Subdashboard {
    private final String dashboardName;
    private final String fieldName;

    protected DashboardFieldBase(String dashboardName, String fieldName) {
        this.dashboardName = dashboardName;
        this.fieldName = fieldName;
    }

    @Override
    public final String getSuperdashboardName() {
        return dashboardName;
    }

    @Override
    public final String getSubdashboardName() {
        return fieldName;
    }
}
