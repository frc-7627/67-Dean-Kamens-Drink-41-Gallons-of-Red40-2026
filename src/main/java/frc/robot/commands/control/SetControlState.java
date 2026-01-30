package frc.robot.commands.control;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.controlstate.ControlState;
import frc.robot.subsystems.controlstate.ControlStateSetter;

public class SetControlState extends InstantCommand {
    public SetControlState(ControlStateSetter controlStateSetter, ControlState controlState) {
        super(() -> controlStateSetter.setControlState(controlState), controlStateSetter);
    }
}
