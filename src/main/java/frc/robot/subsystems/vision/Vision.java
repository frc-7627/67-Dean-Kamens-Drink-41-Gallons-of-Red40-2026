package frc.robot.subsystems.vision;

public interface Vision extends BestTargetSupplier, VisionOdometry {
    static Vision get() throws VisionConstructorException {
        return new PhotonCameras();
    }
}
