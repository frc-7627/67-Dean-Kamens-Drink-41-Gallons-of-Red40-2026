package frc.robot.resources.vision;

public interface Vision extends BestTargetSupplier, VisionMeasurementsSupplier {
    static Vision create() throws VisionConstructorException {
        return new PhotonCameras();
    }
}
