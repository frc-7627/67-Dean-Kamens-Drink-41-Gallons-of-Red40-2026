package frc.robot.resources.vision;

import static edu.wpi.first.units.Units.Microseconds;
import static edu.wpi.first.units.Units.Milliseconds;
import static edu.wpi.first.units.Units.Seconds;
import static frc.robot.Constants.VisionConstants.FIELD_LAYOUT;
import static frc.robot.Constants.VisionConstants.MAX_CONNECTION_RETRIES;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.legacy.vision.dashboard.StandardDeviations;

class PhotonCameraWrapper {
    private static final Logger LOGGER =
            Logger.getLogger(PhotonCameraWrapper.class.getSimpleName());

    private final Transform3d transform;

    private final PhotonCamera photonCamera;

    private final PhotonPoseEstimator poseEstimator;

    private Optional<EstimatedRobotPose> estimatedRobotPoseOptional;

    private List<PhotonPipelineResult> results;

    private double lastReadTimestamp = Microseconds.of(NetworkTablesJNI.now()).in(Seconds);

    private double currentStdDev;

    PhotonCameraWrapper(PhotonCameraInfo cameraInfo) {
        this.transform = cameraInfo.getTransform();
        this.photonCamera = getConnectedPhotonCamera(cameraInfo.getName());
        this.poseEstimator = new PhotonPoseEstimator(FIELD_LAYOUT,
                PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, transform);

        poseEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
    }

    private static PhotonCamera getConnectedPhotonCamera(String name) {
        LOGGER.fine(String.format("Trying to connect to camera '%s'...", name));
        PhotonCamera photonCamera = new PhotonCamera(name);

        int connectionRetries = 0;

        for (; connectionRetries < MAX_CONNECTION_RETRIES
                && !photonCamera.isConnected(); connectionRetries++) {
            LOGGER.fine(String.format("Retrying to connect to camera '%s'... (retry %d)", name,
                    connectionRetries + 1));
            photonCamera = new PhotonCamera(name);
        }

        if (photonCamera.isConnected()) {
            if (connectionRetries == 0) {
                LOGGER.info(String.format("Connected to camera '%s' with no retries!", name));
            } else {
                LOGGER.info(String.format("Connected to camera '%s' after %d retries.", name,
                        connectionRetries));
            }
        } else {
            LOGGER.severe(String.format("Failed to connect to camera '%s'!", name));;
        }

        return photonCamera;
    }

    Optional<PhotonPipelineResult> getLatestResult() {
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    double getCurrentStdDev() {
        return currentStdDev;
    }

    Optional<VisionMeasurement> getVisionMeasurement(StandardDeviations standardDeviations) {
        return getEstimatedPose(standardDeviations)
                .map(estimatedRobotPose -> new PhotonVisionMeasurement(estimatedRobotPose,
                        currentStdDev));
    }

    Optional<EstimatedRobotPose> getEstimatedPose(StandardDeviations standardDeviations) {
        updateUnreadResults(standardDeviations);
        return estimatedRobotPoseOptional;
    }

    private void updateUnreadResults(StandardDeviations standardDeviations) {
        double mostRecentTimestamp = results.isEmpty() ? 0.0 : results.get(0).getTimestampSeconds();
        double currentTimestamp = Microseconds.of(NetworkTablesJNI.now()).in(Seconds);
        double debounceTime = Milliseconds.of(15).in(Seconds);
        for (PhotonPipelineResult result : results) {
            mostRecentTimestamp = Math.max(mostRecentTimestamp, result.getTimestampSeconds());
        }
        if ((results.isEmpty() || (currentTimestamp - mostRecentTimestamp >= debounceTime))
                && (currentTimestamp - lastReadTimestamp) >= debounceTime) {
            results.clear();
            results.addAll(photonCamera.getAllUnreadResults());
            lastReadTimestamp = currentTimestamp;
            results.sort((PhotonPipelineResult a, PhotonPipelineResult b) -> {
                return a.getTimestampSeconds() >= b.getTimestampSeconds() ? 1 : -1;
            });
            if (!results.isEmpty()) {
                updateEstimatedGlobalPose(standardDeviations);
            }
        }
    }

    private void updateEstimatedGlobalPose(StandardDeviations standardDeviations) {
        Optional<EstimatedRobotPose> estimatedRobotPoseOptional = Optional.empty();

        for (PhotonPipelineResult result : results) {
            estimatedRobotPoseOptional = poseEstimator.update(result);
            updateEstimationStdDevs(standardDeviations, result.getTargets());
        }

        this.estimatedRobotPoseOptional = estimatedRobotPoseOptional;
    }

    private void updateEstimationStdDevs(StandardDeviations standardDeviations, List<PhotonTrackedTarget> targets) {
        final double singleTagStdDev = standardDeviations.getSingleTagStdDev();

        if (estimatedRobotPoseOptional.isEmpty()) {
            // No pose input. Default to single-tag std devs
            currentStdDev = singleTagStdDev;

        } else {
            // Pose present. Start running Heuristic
            double estimatedStdDev = singleTagStdDev;
            int numTags = 0;
            double avgDist = 0;

            // Precalculation - see how many tags we found, and calculate an
            // average-distance metric
            for (PhotonTrackedTarget target : targets) {
                Optional<Pose3d> tagPoseOptional = poseEstimator.getFieldTags().getTagPose(target.getFiducialId());
                if (tagPoseOptional.isEmpty()) {
                    continue;
                }
                numTags++;
                avgDist += tagPoseOptional.get().toPose2d().getTranslation()
                        .getDistance(estimatedRobotPoseOptional.get().estimatedPose.toPose2d().getTranslation());
            }

            // Debug
            SmartDashboard.putNumber("Vision/Tags Seen", numTags);

            if (numTags == 0) {
                // No tags visible. Default to single-tag std devs
                currentStdDev = singleTagStdDev;
            } else {
                // One or more tags visible, run the full heuristic.
                avgDist /= numTags;
                // Decrease std devs if multiple targets are visible
                if (numTags > 1) {
                    estimatedStdDev = standardDeviations.getMultiTagStdDev();
                }
                // Increase std devs based on (average) distance
                if (numTags == 1 && avgDist > 3) // Assuming Max Distance before tag was
                                                 // invalid: was 4
                                                 // before
                {
                    estimatedStdDev = Double.MAX_VALUE;
                } else {
                    estimatedStdDev = estimatedStdDev * (1 + (avgDist * avgDist / 30));
                }
                currentStdDev = estimatedStdDev;
            }
        }
    }
}
