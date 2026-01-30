package frc.robot.resources.vision;

import static frc.robot.Constants.VisionConstants.FIELD_LAYOUT;
import java.util.Optional;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;

class PhotonTrackedTargetWrapper implements ComparableTarget<PhotonTrackedTargetWrapper> {
    private final PhotonTrackedTarget photonTrackedTarget;

    PhotonTrackedTargetWrapper(PhotonTrackedTarget photonTrackedTarget) {
        this.photonTrackedTarget = photonTrackedTarget;
    }

    @Override
    public Optional<Pose2d> getPose() {
        return FIELD_LAYOUT.getTagPose(photonTrackedTarget.getFiducialId()).map(Pose3d::toPose2d);
    }

    @Override
    public boolean compareWith(PhotonTrackedTargetWrapper other) {
        return photonTrackedTarget.getPoseAmbiguity() < other.photonTrackedTarget.getPoseAmbiguity();
    }
}
