package frc.robot.resources.vision;

import java.util.Optional;
import edu.wpi.first.math.geometry.Pose2d;

public interface BestTargetSupplier {
    Optional<Target> getBestTarget();

    default Optional<Pose2d> getBestTargetPose() {
        return getBestTarget().flatMap(Target::getPose);
    }
}
