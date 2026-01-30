package frc.robot.resources.vision;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.numbers.N3;

/**
 * Interface that contains the vision measurements of the robot.
 */
public interface VisionMeasurement {

    /**
     * Gets the measured pose (position and rotation) of the robot, as reported by
     * odometry.
     *
     * @return The robot's pose
     */
    Pose2d getPose();

    /**
     * get the estimated time the frame used to derive the robot pose was taken and
     * convert it to a timestamp
     * 
     * @return timestamp of when robot pose was calculated
     */
    double getTimestamp();

    Vector<N3> getStdDevs();
}
