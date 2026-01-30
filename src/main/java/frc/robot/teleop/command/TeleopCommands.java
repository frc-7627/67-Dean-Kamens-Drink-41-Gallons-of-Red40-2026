package frc.robot.teleop.command;

import java.util.List;
import frc.robot.subsystems.indication.Indicator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.controlstate.ControlState;
import frc.robot.subsystems.controlstate.ControlStateToggler;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.teleop.controller.TeleopController;

public class TeleopCommands {
    private final List<TeleopCommandFactory> teleopCommandFactories;
    private final CommandContext commandContext;

    public TeleopCommands(Indicator indicator, Drivebase drivebase, Intake intake,
            ControlStateToggler controlStateSetter) {
        this.commandContext = new CommandContext(indicator, drivebase, intake, controlStateSetter);

        this.teleopCommandFactories = List.of(TeleopCommandFactory.values());
    }

    public void bindToController(TeleopController controller, ControlState controlState) {
        teleopCommandFactories.forEach(factory -> {
            controller.bindCommand(factory, factory.getBinderConsumer(commandContext),
                    controlState);
        });
    }
}
