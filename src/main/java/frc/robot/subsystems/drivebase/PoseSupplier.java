package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.geometry.Pose2d;

/**
 * Interface supplying the position/pose of the robot on the field currently.
 */
public interface PoseSupplier {
    /**
     * Gets the measured pose (position and rotation) of the robot, as reported by
     * odometry.
     *
     * @return The robot's pose
     */
    Pose2d getPose();
}
