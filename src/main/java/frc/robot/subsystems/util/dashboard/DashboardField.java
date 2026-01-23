package frc.robot.subsystems.util.dashboard;

/**
 * A dashboard field.
 * 
 * @see BaseDashboardField
 * @see #init()
 * @see #update()
 */
public interface DashboardField {
    /**
     * Initialize the field.
     * 
     * This should guarantee that the dashboard can see the field.
     * 
     * @see #initAll(DashboardField[])
     */
    void init();

    /**
     * Update the field.
     * 
     * This should guarantee that the dashboard and the robot agree on the field's
     * value.
     * 
     * @see #updateAll(DashboardField[])
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
