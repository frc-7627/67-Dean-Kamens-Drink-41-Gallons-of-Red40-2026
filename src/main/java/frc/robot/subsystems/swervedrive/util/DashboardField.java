package frc.robot.subsystems.swervedrive.util;

import java.util.List;

/**
 * A class that can send and receive it's instances to and from the dashboard.
 */
public interface DashboardField<Inner> {
    /**
     * @return The inner value
     */
    Inner getInnerValue();

    /**
     * Set the inner value.
     * 
     * @param inner the inner value.
     */
    void setInnerValue(Inner inner);

    /**
     * Push the field to the dashboard.
     */
    void push();

    /**
     * Pull the field from the dashboard.
     */
    void pull();

    /**
     * Update the field.
     * 
     * Push and pull the field to and from the dashboard.
     */
    default void update() {
        push();
        pull();
    }

    /**
     * Update all the provided fields.
     * 
     * @param fields the provided fields.
     */
    public static void updateAll(List<DashboardField<?>> fields) {
        for (DashboardField<?> field : fields) {
            field.update();
        }
    }
}
