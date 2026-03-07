package frc.robot.subsystems.controllable.drivebase;

import frc.bofalib.loggable.Loggable;

/**
 * Strategies for distance targeting.
 */
public interface DistanceTargetter extends Loggable {
    /**
     * Initialize.
     */
    default void initialize() {
    }

    /**
     * @return the current target distance of the robot, in meters
     */
    double getTargetMeters();
}
