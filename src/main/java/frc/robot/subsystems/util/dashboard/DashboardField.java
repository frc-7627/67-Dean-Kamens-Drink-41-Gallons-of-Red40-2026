package frc.robot.subsystems.util.dashboard;

public interface DashboardField {
    void init();

    void update();

    static void initAll(DashboardField[] dashboardFields) {
        for (DashboardField dashboardField : dashboardFields) {
            dashboardField.init();
        }
    }

    static void updateAll(DashboardField[] dashboardFields) {
        for (DashboardField dashboardField : dashboardFields) {
            dashboardField.update();
        }
    }
}
