package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.geometry.Pose2d;

public interface PoseSupplier {
    /**
     * Gets the measured pose (position and rotation) of the robot, as reported by odometry.
     *
     * @return The robot's pose
     */
    Pose2d getPose();
}
