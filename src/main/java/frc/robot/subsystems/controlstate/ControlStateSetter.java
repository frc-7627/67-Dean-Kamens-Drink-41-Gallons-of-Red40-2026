package frc.robot.subsystems.controlstate;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface ControlStateSetter extends Subsystem {
    void setControlState(ControlState controlState);
}
