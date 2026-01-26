package frc.robot.subsystems.swervedrive.vision;

import java.util.List;
import java.util.Optional;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;

/**
 * The cameras.
 */
public class Cameras {
    private final Camera[] cameras = Camera.values();

    /**
     * @return all targets from all cameras.
     */
    private List<PhotonTrackedTarget> getAllTargets() {
        List<PhotonTrackedTarget> allTargets = List.of();

        for (Camera camera : cameras) {
            // Get the latest result from the camera.
            camera.getLatestResult()
                    // Get the targets from the latest camera result, if present.
                    .map(latestCameraResult -> latestCameraResult.getTargets())
                    // If the latest camera result targets are present, add them.
                    .ifPresent(latestCameraResultTargets -> {
                        allTargets.addAll(latestCameraResultTargets);
                    });
        }

        return allTargets;
    }

    /**
     * @return if there is one, the best target, otherwise, an empty optional.
     */
    public Optional<PhotonTrackedTarget> getBestTarget() {
        Optional<PhotonTrackedTarget> bestTargetOptional = Optional.empty();

        for (PhotonTrackedTarget target : getAllTargets()) {
            if (bestTargetOptional.isPresent()) {
                PhotonTrackedTarget prevBestTarget = bestTargetOptional.get();

                if (target.getPoseAmbiguity() >= prevBestTarget.getPoseAmbiguity()) {
                    // In this case, the previous best target exists, and it is better than the
                    // current target already.
                    continue;
                }
            }

            // If there is no best target yet, or there is and this target has a lower pose
            // ambiguity, then this target is the new best target.
            bestTargetOptional = Optional.of(target);
        }

        return bestTargetOptional;
    }

    /**
     * Get all target poses from the field layout.
     * 
     * @param fieldLayout the field layout.
     * @return all target poses.
     */
    public List<Pose2d> getAllTargetPoses(AprilTagFieldLayout fieldLayout) {
        List<Pose2d> allTargetPoses = List.of();

        for (PhotonTrackedTarget target : getAllTargets()) {
            // Get the target's pose 3d using the field layout.
            fieldLayout.getTagPose(target.getFiducialId())
                    // Project the target pose 3d to 2d.
                    .map(targetPose3d -> targetPose3d.toPose2d())
                    // Add the target pose.
                    .ifPresent(targetPose -> {
                        allTargetPoses.add(targetPose);
                    });

        }

        return allTargetPoses;
    }

    /**
     * Get the best target pose from the field layout.
     * 
     * @param fieldLayout the field layout.
     * @return the best target pose.
     */
    public Optional<Pose2d> getBestTargetPose(AprilTagFieldLayout fieldLayout) {
        // Get the best target.
        return getBestTarget()
            // Get the pose 3d using the target, if present, and the field layout.
            .flatMap(target -> fieldLayout.getTagPose(target.getFiducialId()))
            // Project the target pose 3d to 2d.
            .map(targetPose3d -> targetPose3d.toPose2d());
    }
}
