package frc.robot.subsystems.swervedrive.vision;

import static frc.robot.Constants.VisionConstants.*;
import java.util.List;
import java.util.Optional;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.subsystems.swervedrive.vision.dashboard.StandardDeviations;
import swervelib.SwerveDrive;

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
     * @return all target poses.
     */
    public List<Pose2d> getAllTargetPoses() {
        List<Pose2d> allTargetPoses = List.of();

        for (PhotonTrackedTarget target : getAllTargets()) {
            // Get the target's pose 3d using the field layout.
            FIELD_LAYOUT.getTagPose(target.getFiducialId())
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
     * @return the best target pose.
     */
    public Optional<Pose2d> getBestTargetPose() {
        // Get the best target.
        return getBestTarget()
                // Get the pose 3d using the target, if present, and the field layout.
                .flatMap(target -> FIELD_LAYOUT.getTagPose(target.getFiducialId()))
                // Project the target pose 3d to 2d.
                .map(targetPose3d -> targetPose3d.toPose2d());
    }

    public void addAllToVisionSystemSim(VisionSystemSim visionSystemSim) {
        for (Camera camera : cameras) {
            camera.addToVisionSim(visionSystemSim);
        }
    }

    public void updatePoseEstimation(SwerveDrive swerveDrive, VisionSim visionSim,
            StandardDeviations standardDeviations, boolean isSimulation) {
        for (Camera camera : cameras) {
            Optional<EstimatedRobotPose> estimatedPoseOptional =
                    camera.getEstimatedGlobalPose(standardDeviations);

            if (isSimulation) {
                estimatedPoseOptional.ifPresentOrElse(estimatedPose -> {
                    visionSim.updateVisionEstimationWithPose(estimatedPose);
                }, () -> {
                    visionSim.updateVisionEstimation();
                });
            }

            estimatedPoseOptional.ifPresent(estimatedPose -> {
                final double stdDev = camera.getCurrentStdDev();
                swerveDrive.addVisionMeasurement(estimatedPose.estimatedPose.toPose2d(),
                        estimatedPose.timestampSeconds, VecBuilder.fill(stdDev, stdDev, stdDev));
            });
        }
    }
}
