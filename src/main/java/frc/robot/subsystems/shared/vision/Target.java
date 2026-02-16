package frc.robot.subsystems.shared.vision;

import java.util.Optional;
import edu.wpi.first.math.geometry.Pose2d;

/**
 * Interface that contains the robots position and rotation according to the
 * odometry.
 */
public interface Target {

    /**
     * Gets the measured pose (position and rotation) of the robot, as reported by
     * odometry.
     *
     * @return The robot's pose
     */
    Optional<Pose2d> getPose();
}
