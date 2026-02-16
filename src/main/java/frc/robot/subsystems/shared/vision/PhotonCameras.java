package frc.robot.subsystems.shared.vision;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.subsystem.SharedSubsystemBase;

final class PhotonCameras extends SharedSubsystemBase implements Vision {
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of("Vision");

    private final List<PhotonCameraWrapper> photonCameraWrappers;

    private final StandardDeviations standardDeviations =
            new StandardDeviations(KEY_BUILDER);

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
                .getVisionMeasurement(standardDeviations).stream());
    }
}
