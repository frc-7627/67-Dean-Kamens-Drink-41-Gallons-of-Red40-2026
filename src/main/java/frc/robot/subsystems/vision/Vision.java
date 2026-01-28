package frc.robot.subsystems.vision;

public interface Vision extends BestTargetSupplier, VisionMeasurementsSupplier {
    static Vision get() throws VisionConstructorException {
        return new PhotonCameras();
    }
}
