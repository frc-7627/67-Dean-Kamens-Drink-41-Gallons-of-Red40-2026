package frc.robot.subsystems.vision;

public interface Vision extends BestTargetSupplier, VisionMeasurementsSupplier {
    static Vision create() throws VisionConstructorException {
        return new PhotonCameras();
    }
}
