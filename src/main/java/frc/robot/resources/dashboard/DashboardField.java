package frc.robot.resources.dashboard;

import frc.robotlib.resource.Subresource;

public interface DashboardField extends Subresource {
    String getDashboardName();

    String getFieldName();

    default String getKeyName() {
        return getDashboardName() + "/" + getFieldName();
    }
}
