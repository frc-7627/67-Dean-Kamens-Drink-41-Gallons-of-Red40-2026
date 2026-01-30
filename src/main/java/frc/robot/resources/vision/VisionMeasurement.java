package frc.robot.resources.vision;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.numbers.N3;

public interface VisionMeasurement {

    /**
     * Gets the measured pose (position and rotation) of the robot, as reported by
     * odometry.
     *
     * @return The robot's pose
     */
    Pose2d getPose();

    double getTimestamp();

    Vector<N3> getStdDevs();
}
