package frc.robot.subsystems.swervedrive.util;

/**
 * A class that can send and receive it's instances to and from the dashboard.
 */
public sealed interface DashboardValue<Value extends DashboardValue<Value>>
        permits DashboardDouble, DashboardBoolean, DashboardString {
    /**
     * Receive the value with the provided key from the dashboard, using this value as the default
     * value.
     * 
     * @param key the provided key.
     * @return the received value.
     */
    Value recv(String key);

    /**
     * Send this value to the dashboard with the provided key.
     * 
     * @param key the provided key.
     */
    void send(String key);
}
