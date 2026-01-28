package frc.robot.subsystems.legacy.vision;

import org.photonvision.EstimatedRobotPose;

public record VisionMeasurement(EstimatedRobotPose estimatedPose, double stdDev) {
    
}
