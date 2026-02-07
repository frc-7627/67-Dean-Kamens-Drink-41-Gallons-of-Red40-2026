package frc.robot.subsystems.vision;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import frc.bofalib.subsystem.PseudoSubsystemBase;

class PhotonCameras extends PseudoSubsystemBase implements Vision {
    private static final String DASHBOARD_NAME = Vision.class.getName();

    private final List<PhotonCameraWrapper> photonCameraWrappers;

    private final StandardDeviationsSubdashboard standardDeviations =
            new StandardDeviationsSubdashboard(DASHBOARD_NAME);

    PhotonCameras() {
        this.photonCameraWrappers = List.of(PhotonCameraInfo.values()).stream()
                .map(photonCameraInfo -> new PhotonCameraWrapper(photonCameraInfo)).toList();
    }

    /**
     * @return all targets from all cameras.
     */
    private Stream<PhotonTrackedTargetWrapper> getBestTargets() {
        return photonCameraWrappers.stream()
                .map(photonCameraWrapper -> photonCameraWrapper.getLatestResult())
                .map(resultOptional -> resultOptional.map(result -> result.getBestTarget()))
                .flatMap(bestTargetOptional -> bestTargetOptional.stream())
                .map(photonTrackedTarget -> new PhotonTrackedTargetWrapper(photonTrackedTarget));
    }

    @Override
    public Optional<Target> getBestTarget() {
        return getBestTargets().max((a, b) -> a.compareWith(b))
                .map(comparableTarget -> (Target) comparableTarget);
    }

    @Override
    public Stream<VisionMeasurement> getVisionMeasurements() {
        return photonCameraWrappers.stream().flatMap(photonCameraWrapper -> photonCameraWrapper
                .getVisionMeasurement(standardDeviations.get()).stream());
    }
}
