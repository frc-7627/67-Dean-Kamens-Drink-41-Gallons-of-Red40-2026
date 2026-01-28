package frc.robot.subsystems.vision;

import org.photonvision.EstimatedRobotPose;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.numbers.N3;

public class PhotonVisionMeasurement implements VisionMeasurement {
    private final EstimatedRobotPose estimatedRobotPose;
    private final double stdDev;

    public PhotonVisionMeasurement(EstimatedRobotPose estimatedRobotPose, double stdDev) {
        this.estimatedRobotPose = estimatedRobotPose;
        this.stdDev = stdDev;
    }

    @Override
    public Pose2d getPose() {
        return estimatedRobotPose.estimatedPose.toPose2d();
    }

    @Override
    public double getTimestamp() {
        return estimatedRobotPose.timestampSeconds;
    }

    @Override
    public Vector<N3> getStdDevs() {
        return VecBuilder.fill(stdDev, stdDev, stdDev);
    }
}
