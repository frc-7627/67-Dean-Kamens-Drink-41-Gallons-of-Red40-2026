package frc.robot.resources.vision;

public interface Vision extends BestTargetSupplier, VisionMeasurementsSupplier {
    static Vision create() {
        return new PhotonCameras();
    }
}
