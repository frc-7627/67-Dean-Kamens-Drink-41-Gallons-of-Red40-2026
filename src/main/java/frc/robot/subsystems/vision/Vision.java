package frc.robot.subsystems.vision;

import frc.bofalib.subsystem.SharedSubsystem;

/**
 * Interface for supplying the best target and vision measurements for robot.
 */
public interface Vision extends BestTargetSupplier, VisionMeasurementsSupplier, SharedSubsystem {
    static Vision create() {
        return new PhotonCameras();
    }
}