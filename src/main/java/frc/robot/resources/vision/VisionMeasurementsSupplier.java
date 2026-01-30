package frc.robot.resources.vision;

import java.util.stream.Stream;
import frc.robot.resources.Resource;

/**
 * Interface that supplies the vision measurements used to update odemetry.
 */
public interface VisionMeasurementsSupplier extends Resource {
    Stream<VisionMeasurement> getVisionMeasurements();
}
