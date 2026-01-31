package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface DirectDrivebase extends Subsystem, PoseSupplier {
    /**
     * Drive with the provided robot-relative speeds.
     * 
     * @param chassisSpeeds the provided robot-relative speeds.
     */
    void driveWithSpeeds(ChassisSpeeds chassisSpeeds);

    /**
     * Forces the robot to keep the current pose.
     */
    void lock();
}
