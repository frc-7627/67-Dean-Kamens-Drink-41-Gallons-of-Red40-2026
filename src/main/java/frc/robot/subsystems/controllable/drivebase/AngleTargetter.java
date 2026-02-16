package frc.robot.subsystems.controllable.drivebase;

/**
 * Strategies for angle targeting.
 */
public interface AngleTargetter {
    /**
     * Initialize.
     */
    default void initialize() {}

    /**
     * @return the current target orientation of the robot, in radians
     */
    double getTargetRadians();
}
