package frc.robot.subsystems.drivebase;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface AutoDrive extends Subsystem {
    PathConstraints getPathConstraints();

    Pose2d getPose();
}
