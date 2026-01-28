package frc.robot.subsystems.vision;

import java.util.Optional;
import edu.wpi.first.math.geometry.Pose2d;

public interface BestTargetSupplier {
    Target getBestTarget();

    default Optional<Pose2d> getBestTargetPose() {
        return getBestTarget().getPose();
    }
}
