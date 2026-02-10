package frc.robot.subsystems.drivebase;

import frc.bofalib.subsystem.ControllableSubsystem;

/**
 * Interface for the drivetrain subsystem, essentially the robot's movement
 * system and acting as the robot's body.
 */
public interface DirectDrivebase extends ControllableSubsystem<DriveControl> {
    void drive();
}
