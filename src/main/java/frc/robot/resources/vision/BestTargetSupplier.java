package frc.robot.resources.vision;

import java.util.Optional;
import edu.wpi.first.math.geometry.Pose2d;

public interface BestTargetSupplier {

    /**
     * Returns the best target in this pipeline result. If there are no targets,
     * this method will
     * return null. The best target is determined by the target sort mode in the
     * PhotonVision UI.
     *
     * @return The best target of the pipeline result.
     */
    Target getBestTarget();

    default Optional<Pose2d> getBestTargetPose() {
        return getBestTarget().getPose();
    }
}
