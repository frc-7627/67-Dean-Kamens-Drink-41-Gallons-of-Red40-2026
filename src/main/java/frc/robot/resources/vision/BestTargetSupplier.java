package frc.robot.resources.vision;

import java.util.Optional;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robotlib.resource.SharedResource;

/**
 * Interface that supplies the pose of the best target for the robot.
 */
public interface BestTargetSupplier extends SharedResource {

    /**
     * Returns the best target in this pipeline result. If there are no targets,
     * this method will
     * return null. The best target is determined by the target sort mode in the
     * PhotonVision UI.
     *
     * @return The best target of the pipeline result.
     */
    Optional<Target> getBestTarget();

    /**
     * Gets the positon of the best target
     * 
     * @return the position of the best target
     */
    default Optional<Pose2d> getBestTargetPose() {
        return getBestTarget().flatMap(Target::getPose);
    }
}
