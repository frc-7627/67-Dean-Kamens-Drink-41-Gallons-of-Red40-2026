package frc.robot.subsystems.vision;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.numbers.N3;

public interface VisionMeasurement {
    Pose2d getPose();

    double getTimestamp();

    Vector<N3> getStdDevs();
}
