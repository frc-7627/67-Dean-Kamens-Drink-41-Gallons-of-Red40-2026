package frc.robot.subsystems.shared.vision;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.subsystem.SharedSubsystemBase;

final class PhotonCameras extends SharedSubsystemBase implements Vision {
    private static final Logger LOGGER = Logger.getLogger(PhotonCameras.class.getName());
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of("Vision");

    private final List<PhotonCameraWrapper> photonCameraWrappers;

    private final StandardDeviations standardDeviations =
            new StandardDeviations(KEY_BUILDER);

    PhotonCameras() {
        this.photonCameraWrappers = List.of(PhotonCameraInfo.values()).stream()
                .map(photonCameraInfo -> new PhotonCameraWrapper(photonCameraInfo)).toList();

        SmartDashboard.putData(
            KEY_BUILDER.copyExtendedToString("Restart Vision"), 
            Commands.runOnce(this::restart)
        );

        SmartDashboard.putData(
            KEY_BUILDER.copyExtendedToString("Restart Vision (devices only)"), 
            Commands.runOnce(this::restartDevicesOnly)
        );
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
        return getBestTargets().min((a, b) -> a.compareWith(b))
                .map(comparableTarget -> (Target) comparableTarget);
    }

    @Override
    public Stream<VisionMeasurement> getVisionMeasurements() {
        return photonCameraWrappers.stream().flatMap(photonCameraWrapper -> photonCameraWrapper
                .getVisionMeasurement(standardDeviations).stream());
    }

    private void restart() {
        sendPhotonVisionRequest("10.76.27.15", "restartProgram");
    }

    private void restartDevicesOnly() {
        sendPhotonVisionRequest("10.76.27.10", "restartDevice");
        sendPhotonVisionRequest("10.76.27.11", "restartDevice");
    }

    private void sendPhotonVisionRequest(String ipString, String command) {
        final String uriString = "http://" + ipString + ":5800/api/utils/" + command;
        try {
            final HttpClient httpClient = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uriString))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build()
            ;
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception error) {
            LOGGER.log(
                Level.SEVERE, 
                error, 
                () -> "Error while sending photon vision request with URI '"
                    + uriString
                    + "'"
            );
        }
    }
}
