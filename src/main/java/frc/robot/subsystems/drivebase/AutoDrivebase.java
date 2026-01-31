package frc.robot.subsystems.drivebase;

import java.util.function.Function;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface AutoDrivebase extends Subsystem {
    PathConstraints getPathConstraints();

    /**
     * Gets the measured pose (position and rotation) of the robot, as reported by odometry.
     *
     * @return The robot's pose
     */
    Pose2d getPose();

    /**
     * Gets a controller that returns chassis speeds that rotate the robot given the difference
     * between the current rotation and target rotation.
     * 
     * @return a rotation controller.
     */
    Function<Rotation2d, ChassisSpeeds> getRotationControl();
}
