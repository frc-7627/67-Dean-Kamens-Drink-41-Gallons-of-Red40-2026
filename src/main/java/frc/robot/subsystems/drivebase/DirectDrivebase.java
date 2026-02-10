package frc.robot.subsystems.drivebase;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Interface for the drivetrain subsystem, essentially the robot's movement
 * system and acting as the robot's body.
 */
public interface DirectDrivebase extends Subsystem {
    void setDriveControl(DriveControl driveControl);

    void drive();
}
