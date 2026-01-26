package frc.robot.subsystems.swervedrive.vision;

import static edu.wpi.first.units.Units.Microseconds;
import static edu.wpi.first.units.Units.Milliseconds;
import static edu.wpi.first.units.Units.Seconds;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.SimCameraProperties;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.VisionConstants.*;
import frc.robot.Robot;
import frc.robot.subsystems.swervedrive.vision.dashboard.StandardDeviations;

/**
 * Camera Enum to select each camera
 */
public enum Camera {
    /**
     * Left camera.
     */
    LEFT_CAMERA(LEFT_CAMERA_NAME, LEFT_CAMERA_TRANSFORM),
    /**
     * Right camera.
     */
    RIGHT_CAMERA(RIGHT_CAMERA_NAME, RIGHT_CAMERA_TRANSFORM);

    // TODO: What is the below message referring to?
    // TODO: PUT BACK OR YOU WILL DIE

    private static final Logger LOGGER = Logger.getLogger(Camera.class.getSimpleName());

    /**
     * Latency alert to use when high latency is detected.
     */
    public final Alert latencyAlert;
    /**
     * Camera instance for comms.
     */
    private final PhotonCamera innerPhotonCamera;
    /**
     * Pose estimator for camera.
     */
    public final PhotonPoseEstimator poseEstimator;
    /**
     * Transform of the camera rotation and translation relative to the center of the robot
     */
    private final Transform3d transform;
    /**
     * Current standard deviations used.
     */
    private double currentStdDev;
    /**
     * Estimated robot pose.
     */
    public Optional<EstimatedRobotPose> estimatedRobotPose = Optional.empty();

    /**
     * Simulated camera instance which only exists during simulations.
     */
    public final PhotonCameraSim cameraSim;
    /**
     * Results list to be updated periodically and cached to avoid unnecessary queries.
     */
    public List<PhotonPipelineResult> resultsList = new ArrayList<>();
    /**
     * Last read from the camera timestamp to prevent lag due to slow data fetches.
     */
    private double lastReadTimestamp = Microseconds.of(NetworkTablesJNI.now()).in(Seconds);

    /**
     * Construct a Photon Camera class with help. Standard deviations are fake values, experiment
     * and determine estimation noise on an actual robot.
     *
     * @param name Name of the PhotonVision camera found in the PV UI.
     * @param robotToCamRotation {@link Rotation3d} of the camera.
     * @param robotToCamTranslation {@link Translation3d} relative to the center of the robot.
     * @param singleTagStdDevs Single AprilTag standard deviations of estimated poses from the
     *        camera.
     * @param multiTagStdDevsMatrix Multi AprilTag standard deviations of estimated poses from the
     *        camera.
     */
    Camera(String name, Transform3d transform) {
        this.transform = transform;
        this.latencyAlert =
                new Alert(String.format("Camera '%s' is experiencing high latency.", name),
                        AlertType.kWarning);

        this.innerPhotonCamera = getPhotonCamera(name);

        this.poseEstimator = new PhotonPoseEstimator(FIELD_LAYOUT,
                PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, transform);
        poseEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);

        if (Robot.isSimulation()) {
            this.cameraSim = new PhotonCameraSim(innerPhotonCamera, SIM_CAMERA_PROPERTIES);
            cameraSim.enableDrawWireframe(DRAW_WIREFRAME);
        } else {
            this.cameraSim = null;
        }
    }

    /**
     * Try to get a connected photon camera with the provided name.
     * 
     * @param name the provided name.
     * @return a photon camera with the provided name, connected if possible.
     */
    private static PhotonCamera getPhotonCamera(String name) {
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
            LOGGER.severe(String.format("Failed to connect to camera '%s'!", name));
        }

        return photonCamera;
    }

    /**
     * Add camera to {@link VisionSystemSim} for simulated photon vision.
     *
     * @param systemSim {@link VisionSystemSim} to use.
     */
    public void addToVisionSim(VisionSystemSim systemSim) {
        if (Robot.isSimulation()) {
            systemSim.addCamera(cameraSim, transform);
        }
    }

    /**
     * Get the result with the least ambiguity from the best tracked target within the Cache. This
     * may not be the most recent result!
     *
     * @return The result in the cache with the least ambiguous best tracked target. This is not the
     *         most recent result!
     */
    public Optional<PhotonPipelineResult> getBestResult() {
        if (resultsList.isEmpty()) {
            return Optional.empty();
        }

        PhotonPipelineResult bestResult = resultsList.get(0);
        double amiguity = bestResult.getBestTarget().getPoseAmbiguity();
        double currentAmbiguity = 0;
        for (PhotonPipelineResult result : resultsList) {
            currentAmbiguity = result.getBestTarget().getPoseAmbiguity();
            if (currentAmbiguity < amiguity && currentAmbiguity > 0) {
                bestResult = result;
                amiguity = currentAmbiguity;
            }
        }
        return Optional.of(bestResult);
    }

    /**
     * Get the latest result from the current cache.
     *
     * @return Empty optional if nothing is found. Latest result if something is there.
     */
    public Optional<PhotonPipelineResult> getLatestResult() {
        return resultsList.isEmpty() ? Optional.empty() : Optional.of(resultsList.get(0));
    }

    public double getCurrentStdDev() {
        return currentStdDev;
    }

    public Optional<VisionMeasurement> getVisionMeasurement(StandardDeviations standardDeviations) {
        return getEstimatedGlobalPose(standardDeviations).map(
            estimatedRobotPose -> new VisionMeasurement(estimatedRobotPose, currentStdDev)
        );
    }

    /**
     * Get the estimated robot pose. Updates the current robot pose estimation, standard deviations,
     * and flushes the cache of results.
     *
     * @return Estimated pose.
     */
    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(
            StandardDeviations standardDeviations) {
        updateUnreadResults(standardDeviations);
        return estimatedRobotPose;
    }

    /**
     * Update the latest results, cached with a maximum refresh rate of 1req/15ms. Sorts the list by
     * timestamp.
     */
    private void updateUnreadResults(StandardDeviations standardDeviations) {
        double mostRecentTimestamp =
                resultsList.isEmpty() ? 0.0 : resultsList.get(0).getTimestampSeconds();
        double currentTimestamp = Microseconds.of(NetworkTablesJNI.now()).in(Seconds);
        double debounceTime = Milliseconds.of(15).in(Seconds);
        for (PhotonPipelineResult result : resultsList) {
            mostRecentTimestamp = Math.max(mostRecentTimestamp, result.getTimestampSeconds());
        }
        if ((resultsList.isEmpty() || (currentTimestamp - mostRecentTimestamp >= debounceTime))
                && (currentTimestamp - lastReadTimestamp) >= debounceTime) {
            resultsList = Robot.isReal() ? innerPhotonCamera.getAllUnreadResults()
                    : cameraSim.getCamera().getAllUnreadResults();
            lastReadTimestamp = currentTimestamp;
            resultsList.sort((PhotonPipelineResult a, PhotonPipelineResult b) -> {
                return a.getTimestampSeconds() >= b.getTimestampSeconds() ? 1 : -1;
            });
            if (!resultsList.isEmpty()) {
                updateEstimatedGlobalPose(standardDeviations);
            }
        }
    }

    /**
     * The latest estimated robot pose on the field from vision data. This may be empty. This should
     * only be called once per loop.
     *
     * <p>
     * Also includes updates for the standard deviations, which can (optionally) be retrieved with
     * {@link Camera#updateEstimationStdDevs}
     *
     * @return An {@link EstimatedRobotPose} with an estimated pose, estimate timestamp, and targets
     *         used for estimation.
     */
    private void updateEstimatedGlobalPose(StandardDeviations standardDeviations) {
        Optional<EstimatedRobotPose> visionEst = Optional.empty();
        for (var change : resultsList) {
            visionEst = poseEstimator.update(change);
            updateEstimationStdDevs(standardDeviations, visionEst, change.getTargets());
        }
        estimatedRobotPose = visionEst;
    }

    /**
     * Calculates new standard deviations This algorithm is a heuristic that creates dynamic
     * standard deviations based on number of tags, estimation strategy, and distance from the tags.
     *
     * @param estimatedPose The estimated pose to guess standard deviations for.
     * @param targets All targets in this camera frame
     */
    private void updateEstimationStdDevs(StandardDeviations standardDeviations,
            Optional<EstimatedRobotPose> estimatedPose, List<PhotonTrackedTarget> targets) {
        final double singleTagStdDev = standardDeviations.getSingleTagStdDev();
        
        if (estimatedPose.isEmpty()) {
            // No pose input. Default to single-tag std devs
            currentStdDev = singleTagStdDev;

        } else {
            // Pose present. Start running Heuristic
            double estimatedStdDev = singleTagStdDev;
            int numTags = 0;
            double avgDist = 0;

            // Precalculation - see how many tags we found, and calculate an
            // average-distance metric
            for (var tgt : targets) {
                var tagPose = poseEstimator.getFieldTags().getTagPose(tgt.getFiducialId());
                if (tagPose.isEmpty()) {
                    continue;
                }
                numTags++;
                avgDist += tagPose.get().toPose2d().getTranslation()
                        .getDistance(estimatedPose.get().estimatedPose.toPose2d().getTranslation());
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
