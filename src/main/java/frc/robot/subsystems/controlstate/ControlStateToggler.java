package frc.robot.subsystems.controlstate;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Interface that switches the mode of the controls for the robot.
 */
public interface ControlStateToggler extends Subsystem {
    void toggleControlState();
}
