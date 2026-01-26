package frc.robot.subsystems.swervedrive.vision;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.IterativeRobotBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.swervedrive.vision.dashboard.StandardDeviations;
import frc.robot.subsystems.util.dashboard.DashboardField;
import static frc.robot.Constants.VisionConstants.FIELD_LAYOUT;
import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonUtils;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import swervelib.SwerveDrive;
import swervelib.telemetry.SwerveDriveTelemetry;

/**
 * Example PhotonVision class to aid in the pursuit of accurate odometry. Taken from
 * https://gitlab.com/ironclad_code/ironclad-2024/-/blob/master/src/main/java/frc/robot/vision/Vision.java?ref_type=heads
 */
public class Vision {

    /**
     * April Tag Field Layout of the year.
     */
    public static final AprilTagFieldLayout fieldLayout = FIELD_LAYOUT;
    /**
     * Ambiguity defined as a value between (0,1). Used in {@link Vision#filterPose}.
     */
    // private static double maximumAmbiguity = 0.25;
    /**
     * Photon Vision Simulation
     */
    public VisionSystemSim visionSystemSim;
    /**
     * Count of times that the odom thinks we're more than 10meters away from the april tag.
     */
    // private double longDistangePoseEstimationCount = 0;
    /**
     * Current pose from the pose estimator using wheel odometry.
     */
    private Supplier<Pose2d> currentPoseSupplier;
    /**
     * Field from {@link swervelib.SwerveDrive#field}
     */
    private Field2d field2d;

    private double tagsSeen = 0;

    private final Cameras cameras = new Cameras();

    private final StandardDeviations standardDeviations = new StandardDeviations();

    private final DashboardField[] dashboardFields = {standardDeviations,};

    /**
     * 
     * 
     * /** Constructor for the Vision class.
     *
     * @param currentPose Current pose supplier, should reference {@link SwerveDrive#getPose()}
     * @param field Current field, should be {@link SwerveDrive#field}
     */
    public Vision(Supplier<Pose2d> currentPose, Field2d field) {
        this.currentPoseSupplier = currentPose;
        this.field2d = field;

        if (Robot.isSimulation()) {
            visionSystemSim = new VisionSystemSim("Vision");
            visionSystemSim.addAprilTags(fieldLayout);

            for (Camera c : Camera.values()) {
                c.addToVisionSim(visionSystemSim);
            }

            openSimCameraViews();
        }

        // Shuffleboard!
        DashboardField.initAll(dashboardFields);
    }

    public void update() {
        DashboardField.updateAll(dashboardFields);
    }

    /**
     * Open up the photon vision camera streams on the localhost, assumes running photon vision on
     * localhost.
     */
    private void openSimCameraViews() {
        if (Desktop.isDesktopSupported()
                && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            // try
            // {
            // Desktop.getDesktop().browse(new URI("http://localhost:1182/"));
            // Desktop.getDesktop().browse(new URI("http://localhost:1184/"));
            // Desktop.getDesktop().browse(new URI("http://localhost:1186/"));
            // } catch (IOException | URISyntaxException e)
            // {
            // e.printStackTrace();
            // }
        }
    }

    /**
     * Calculates a target pose relative to an AprilTag on the field.
     *
     * @param aprilTag The ID of the AprilTag.
     * @param robotOffset The offset {@link Transform2d} of the robot to apply to the pose for the
     *        robot to position itself correctly.
     * @return The target pose of the AprilTag.
     */
    public static Pose2d getAprilTagPose(int aprilTag, Transform2d robotOffset) {
        Optional<Pose3d> aprilTagPose3d = fieldLayout.getTagPose(aprilTag);
        if (aprilTagPose3d.isPresent()) {
            return aprilTagPose3d.get().toPose2d().transformBy(robotOffset);
        } else {
            throw new RuntimeException(
                    "Cannot get AprilTag " + aprilTag + " from field " + fieldLayout.toString());
        }

    }

    /**
     * Update the pose estimation inside of {@link SwerveDrive} with all of the given poses.
     *
     * @param swerveDrive {@link SwerveDrive} instance.
     */
    public void updatePoseEstimation(SwerveDrive swerveDrive) {
        if (SwerveDriveTelemetry.isSimulation
                && swerveDrive.getSimulationDriveTrainPose().isPresent()) {
            /*
             * In the maple-sim, odometry is simulated using encoder values, accounting for factors
             * like skidding and drifting. As a result, the odometry may not always be 100%
             * accurate. However, the vision system should be able to provide a reasonably accurate
             * pose estimation, even when odometry is incorrect. (This is why teams implement vision
             * system to correct odometry.) Therefore, we must ensure that the actual robot pose is
             * provided in the simulator when updating the vision simulation during the simulation.
             */
            visionSystemSim.update(swerveDrive.getSimulationDriveTrainPose().get());
        }
        for (Camera camera : Camera.values()) {
            Optional<EstimatedRobotPose> poseEst = getEstimatedGlobalPose(camera);
            if (poseEst.isPresent()) {
                var pose = poseEst.get();
                final double stdDev = camera.getCurrentStdDev();
                swerveDrive.addVisionMeasurement(pose.estimatedPose.toPose2d(),
                        pose.timestampSeconds, VecBuilder.fill(stdDev, stdDev, stdDev));
            }
        }

    }

    /**
     * Generates the estimated robot pose. Returns empty if:
     * <ul>
     * <li>No Pose Estimates could be generated</li>
     * <li>The generated pose estimate was considered not accurate</li>
     * </ul>
     *
     * @return an {@link EstimatedRobotPose} with an estimated pose, timestamp, and targets used to
     *         create the estimate
     */
    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Camera camera) {
        Optional<EstimatedRobotPose> poseEst = camera.getEstimatedGlobalPose(standardDeviations);
        if (Robot.isSimulation()) {
            Field2d debugField = visionSystemSim.getDebugField();
            // Uncomment to enable outputting of vision targets in sim.
            poseEst.ifPresentOrElse(est -> debugField.getObject("VisionEstimation")
                    .setPose(est.estimatedPose.toPose2d()), () -> {
                        debugField.getObject("VisionEstimation").setPoses();
                    });
        }
        return poseEst;
    }

    /**
     * Filter pose via the ambiguity and find best estimate between all of the camera's throwing out
     * distances more than 10m for a short amount of time.
     *
     * @param pose Estimated robot pose.
     * @return Could be empty if there isn't a good reading.
     *
     * @Deprecated(since = "2024", forRemoval = true) private Optional<EstimatedRobotPose>
     *                   filterPose(Optional<EstimatedRobotPose> pose) { if (pose.isPresent()) {
     *                   double bestTargetAmbiguity = 1; // 1 is max ambiguity for
     *                   (PhotonTrackedTarget target : pose.get().targetsUsed) { double ambiguity =
     *                   target.getPoseAmbiguity(); if (ambiguity != -1 && ambiguity <
     *                   bestTargetAmbiguity) { bestTargetAmbiguity = ambiguity; } } //ambiguity to
     *                   high dont use estimate if (bestTargetAmbiguity > maximumAmbiguity) { return
     *                   Optional.empty(); }
     * 
     *                   //est pose is very far from recorded robot pose if
     *                   (PhotonUtils.getDistanceToPose(currentPose.get(),
     *                   pose.get().estimatedPose.toPose2d()) > 1) {
     *                   longDistangePoseEstimationCount++;
     * 
     *                   //if it calculates that were 10 meter away for more than 10 times in a row
     *                   its probably right if (longDistangePoseEstimationCount < 10) { return
     *                   Optional.empty(); } } else { longDistangePoseEstimationCount = 0; } return
     *                   pose; } return Optional.empty(); }
     */

    /**
     * Get distance of the robot from the AprilTag pose.
     *
     * @param id AprilTag ID
     * @return Distance
     */
    public double getDistanceFromAprilTag(int id) {
        Optional<Pose3d> tag = fieldLayout.getTagPose(id);
        return tag
                .map(pose3d -> PhotonUtils.getDistanceToPose(currentPoseSupplier.get(), pose3d.toPose2d()))
                .orElse(-1.0);
    }

    /**
     * Update the {@link Field2d} to include tracked targets/
     */
    public void updateVisionField() {

        List<PhotonTrackedTarget> targets = new ArrayList<PhotonTrackedTarget>();
        for (Camera c : Camera.values()) {
            if (!c.resultsList.isEmpty()) {
                PhotonPipelineResult latest = c.resultsList.get(0);
                if (latest.hasTargets()) {
                    targets.addAll(latest.targets);
                }
            }
        }

        List<Pose2d> poses = new ArrayList<>();
        for (PhotonTrackedTarget target : targets) {
            if (fieldLayout.getTagPose(target.getFiducialId()).isPresent()) {
                Pose2d targetPose = fieldLayout.getTagPose(target.getFiducialId()).get().toPose2d();
                poses.add(targetPose);
            }
        }

        field2d.getObject("tracked targets").setPoses(poses);
    }

}
