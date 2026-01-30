package frc.robot.commands.control;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.controlstate.ControlStateToggler;

public class ToggleControlState extends InstantCommand {
    public ToggleControlState(ControlStateToggler controlStateToggler) {
        super(controlStateToggler::toggleControlState, controlStateToggler);
    }
}
