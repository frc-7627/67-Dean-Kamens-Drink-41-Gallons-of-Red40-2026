package frc.robot.resources.vision;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import frc.robot.subsystems.util.dashboard.DashboardField;
import frc.robotlib.resource.SharedResourceBase;

class PhotonCameras extends SharedResourceBase implements Vision {
    private final List<PhotonCameraWrapper> photonCameraWrappers;

    private final StandardDeviations standardDeviations = new StandardDeviations();

    private final DashboardField[] dashboardFields = {standardDeviations,};

    PhotonCameras() {
        this.photonCameraWrappers = List.of(PhotonCameraInfo.values()).stream()
                .map(photonCameraInfo -> new PhotonCameraWrapper(photonCameraInfo)).toList();

        DashboardField.initAll(dashboardFields);
    }

    @Override
    public void periodic() {
        DashboardField.updateAll(dashboardFields);
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
