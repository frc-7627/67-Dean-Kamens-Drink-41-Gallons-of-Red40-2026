package frc.robot.subsystems.vision;

import static frc.robot.Constants.VisionConstants.FIELD_LAYOUT;
import java.util.Optional;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;

public class PhotonTrackedTargetWrapper implements Target {
    private final PhotonTrackedTarget photonTrackedTarget;

    public PhotonTrackedTargetWrapper(PhotonTrackedTarget photonTrackedTarget) {
        this.photonTrackedTarget = photonTrackedTarget;
    }

    @Override
    public Optional<Pose2d> getPose() {
        return FIELD_LAYOUT.getTagPose(photonTrackedTarget.getFiducialId()).map(Pose3d::toPose2d);
    }
}
