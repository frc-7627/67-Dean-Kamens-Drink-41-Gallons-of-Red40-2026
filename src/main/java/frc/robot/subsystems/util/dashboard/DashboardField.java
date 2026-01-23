package frc.robot.subsystems.util.dashboard;

public interface DashboardField {
    /**
     * Initialize the field.
     * 
     * This should guarantee that the dashboard can see the field.
     */
    void init();

    /**
     * Update the field.
     * 
     * This should guarantee that the dashboard and the robot agree on the field's
     * value.
     */
    void update();

    /**
     * Initialize all the provided dashboard fields.
     * 
     * @param dashboardFields the provided dashboard fields.
     * @see #update()
     */
    static void initAll(DashboardField[] dashboardFields) {
        for (DashboardField dashboardField : dashboardFields) {
            dashboardField.init();
        }
    }

    /**
     * Update all the provided dashboard fields.
     * 
     * @param dashboardFields the provided dashboard fields.
     * @see #update()
     */
    static void updateAll(DashboardField[] dashboardFields) {
        for (DashboardField dashboardField : dashboardFields) {
            dashboardField.update();
        }
    }
}
