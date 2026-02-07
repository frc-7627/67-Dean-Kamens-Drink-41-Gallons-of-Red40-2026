package frc.robot.subsystems.vision;

import java.util.stream.Stream;

/**
 * Interface that supplies the vision measurements used to update odemetry.
 */
public interface VisionMeasurementsSupplier {
    Stream<VisionMeasurement> getVisionMeasurements();
}
