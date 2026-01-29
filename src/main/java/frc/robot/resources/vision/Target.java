package frc.robot.resources.vision;

import java.util.Optional;
import edu.wpi.first.math.geometry.Pose2d;

public interface Target {
    Optional<Pose2d> getPose();
}
