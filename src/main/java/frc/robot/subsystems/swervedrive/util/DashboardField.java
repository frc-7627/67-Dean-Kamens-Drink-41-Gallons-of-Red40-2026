package frc.robot.subsystems.swervedrive.util;

/**
 * A class that can send and receive it's instances to and from the dashboard.
 */
interface DashboardField<Inner> {
    /**
     * @return The inner value
     */
    Inner getInnerValue();

    /**
     * Set the inner value.
     * 
     * @param innerValue the inner value.
     */
    void setInnerValue(Inner innerValue);

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
}
