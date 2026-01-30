package frc.robot.resources.vision;

import java.util.stream.Stream;

public interface VisionMeasurementsSupplier {
    Stream<VisionMeasurement> getVisionMeasurements();
}
