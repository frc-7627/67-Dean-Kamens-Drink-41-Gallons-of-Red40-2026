package frc.robot.resources.vision;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.numbers.N3;

final class PhotonVisionMeasurement implements VisionMeasurement {
    private Pose2d estimatedPose;
    private double timestamp;
    private double stdDev;

    PhotonVisionMeasurement() {
        this(new Pose2d(), 0.0, 0.0);
    }

    PhotonVisionMeasurement(Pose2d estimatedPose, double timestamp, double stdDev) {
        this.estimatedPose = estimatedPose;
        this.timestamp = timestamp;
        this.stdDev = stdDev;
    }

    void setEstimatedPose(Pose2d estimatedPose) {
        this.estimatedPose = estimatedPose;
    }
    
    void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    @Override
    public Pose2d getPose() {
        return estimatedPose;
    }

    @Override
    public double getTimestamp() {
        return timestamp;
    }

    @Override
    public Vector<N3> getStdDevs() {
        return VecBuilder.fill(stdDev, stdDev, stdDev);
    }
}
