package frc.robot.subsystems.controllable.drivebase;

import frc.bofalib.loggable.Loggable;

/**
 * Strategies for angle targeting.
 */
public interface AngleTargetter extends Loggable {
    /**
     * Initialize.
     */
    default void initialize() {}

    /**
     * @return the current target orientation of the robot, in radians
     */
    double getTargetRadians();
}
