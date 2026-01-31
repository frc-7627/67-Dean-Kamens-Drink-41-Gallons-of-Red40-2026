package frc.robot.subsystems.drivebase;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;

public interface AutoDrivebase extends CommonDrivebase {
    PathConstraints getPathConstraints();

    /**
     * Gets the measured pose (position and rotation) of the robot, as reported by odometry.
     *
     * @return The robot's pose
     */
    Pose2d getPose();
}
