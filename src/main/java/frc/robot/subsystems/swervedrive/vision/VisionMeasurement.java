package frc.robot.subsystems.swervedrive.vision;

import org.photonvision.EstimatedRobotPose;

public record VisionMeasurement(EstimatedRobotPose estimatedPose, double stdDev) {
    
}
