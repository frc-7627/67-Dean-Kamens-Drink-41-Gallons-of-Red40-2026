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
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.swervedrive.vision.dashboard.StandardDeviations;
import frc.robot.subsystems.util.dashboard.DashboardField;
import static frc.robot.Constants.VisionConstants.*;
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
    private final VisionSim visionSim;

    /**
     * Current pose from the pose estimator using wheel odometry.
     */
    private Supplier<Pose2d> currentPoseSupplier;
    /**
     * Field from {@link swervelib.SwerveDrive#field}
     */
    private Field2d field2d;

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

        this.visionSim = isSimulation() ? new VisionSim(cameras) : null;

        // Shuffleboard!
        DashboardField.initAll(dashboardFields);
    }

    public void update() {
        DashboardField.updateAll(dashboardFields);
    }

    private boolean isSimulation() {
        return Robot.isSimulation();
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
        Optional<Pose3d> aprilTagPose3d = FIELD_LAYOUT.getTagPose(aprilTag);
        if (aprilTagPose3d.isPresent()) {
            return aprilTagPose3d.get().toPose2d().transformBy(robotOffset);
        } else {
            throw new RuntimeException(
                    "Cannot get AprilTag " + aprilTag + " from field " + FIELD_LAYOUT.toString());
        }

    }

    public void updateSimWithDriveTrainPose(Pose2d driveTrainPose) {
        /*
         * In the maple-sim, odometry is simulated using encoder values, accounting for factors like
         * skidding and drifting. As a result, the odometry may not always be 100% accurate.
         * However, the vision system should be able to provide a reasonably accurate pose
         * estimation, even when odometry is incorrect. (This is why teams implement vision system
         * to correct odometry.) Therefore, we must ensure that the actual robot pose is provided in
         * the simulator when updating the vision simulation during the simulation.
         */
        if (isSimulation()) {
            visionSim.updateWithDriveTrainPose(driveTrainPose);
        }
    }

    /**
     * Update the pose estimation inside of {@link SwerveDrive} with all of the given poses.
     *
     * @param swerveDrive {@link SwerveDrive} instance.
     */
    public List<VisionMeasurement> updateAndGetVisionMeasurements() {
        List<VisionMeasurement> visionMeasurements =
                cameras.getAllVisionMeasurements(standardDeviations);

        if (isSimulation()) {
            visionMeasurements.forEach(visionMeasurement -> {
                visionSim.updateVisionEstimationWithPose(visionMeasurement.estimatedPose());
            });
        }

        return visionMeasurements;
    }

    /**
     * Get distance of the robot from the AprilTag pose.
     *
     * @param id AprilTag ID
     * @return Distance
     */
    public double getDistanceFromAprilTag(int id) {
        Optional<Pose3d> tag = FIELD_LAYOUT.getTagPose(id);
        return tag.map(pose3d -> PhotonUtils.getDistanceToPose(currentPoseSupplier.get(),
                pose3d.toPose2d())).orElse(-1.0);
    }

    private FieldObject2d getTrackedTargetsObject() {
        return field2d.getObject(TRACKED_TARGETS_OBJECT_NAME);
    }

    /**
     * Update the {@link Field2d} to include tracked targets/
     */
    public void updateVisionField() {
        getTrackedTargetsObject().setPoses(cameras.getAllTargetPoses());
    }

}
