package frc.robot.subsystems.drivebase.vision;

import org.photonvision.EstimatedRobotPose;

public record VisionMeasurement(EstimatedRobotPose estimatedPose, double stdDev) {
    
}
