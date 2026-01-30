package frc.robot.resources.vision;

/**
 * Interface for supplying the best target and vision measurements for robot.
 */
public interface Vision extends BestTargetSupplier, VisionMeasurementsSupplier {
    static Vision create() {
        return new PhotonCameras();
    }
}