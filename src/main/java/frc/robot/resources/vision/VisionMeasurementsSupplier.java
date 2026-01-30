package frc.robot.resources.vision;

import java.util.stream.Stream;
import frc.robotlib.resource.SharedResource;

/**
 * Interface that supplies the vision measurements used to update odemetry.
 */
public interface VisionMeasurementsSupplier extends SharedResource {
    Stream<VisionMeasurement> getVisionMeasurements();
}
