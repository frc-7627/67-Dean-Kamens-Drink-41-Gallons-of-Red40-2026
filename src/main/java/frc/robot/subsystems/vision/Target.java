package frc.robot.subsystems.vision;

import java.util.Optional;
import edu.wpi.first.math.geometry.Pose2d;

public interface Target {
    Optional<Pose2d> getPose();
}
