package frc.robot.subsystems.legacy.vision;

import static frc.robot.Constants.VisionConstants.*;
import java.awt.Desktop;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.simulation.VisionSystemSim;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;

/**
 * Vision simulation.
 */
public class VisionSim {
    private final VisionSystemSim visionSystemSim;

    public VisionSim(Cameras cameras) {
        this.visionSystemSim = new VisionSystemSim("Vision");
        visionSystemSim.addAprilTags(FIELD_LAYOUT);

        cameras.addAllToVisionSystemSim(visionSystemSim);

        openSimCameraViews();
    }

    private static void openSimCameraViews() {
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

    public void updateWithDriveTrainPose(Pose2d driveTrainPose) {
        visionSystemSim.update(driveTrainPose);
    }

    private FieldObject2d getVisionEstimationObject() {
        return visionSystemSim.getDebugField().getObject(VISION_ESTIMATION_OBJECT_NAME);
    }

    public void updateVisionEstimationWithPose(EstimatedRobotPose estimatedRobotPose) {
        getVisionEstimationObject().setPose(estimatedRobotPose.estimatedPose.toPose2d());
    }
}
