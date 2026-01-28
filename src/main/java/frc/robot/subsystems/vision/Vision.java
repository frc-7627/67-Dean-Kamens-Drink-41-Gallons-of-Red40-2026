package frc.robot.subsystems.vision;

public interface Vision extends TargetSupplier, VisionOdometry {
    static Vision get() throws VisionConstructorException {
        return new PhotonCameras();
    }
}
