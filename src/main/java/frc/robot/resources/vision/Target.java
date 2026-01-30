package frc.robot.resources.vision;

import java.util.Optional;
import edu.wpi.first.math.geometry.Pose2d;

public interface Target {

    /**
     * Gets the measured pose (position and rotation) of the robot, as reported by
     * odometry.
     *
     * @return The robot's pose
     */
    Optional<Pose2d> getPose();
}
