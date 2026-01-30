package frc.robot.teleop.command;

import java.util.List;
import frc.robot.subsystems.controlstate.ControlState;
import frc.robot.teleop.controller.TeleopController;

public class TeleopCommands {
    private final List<TeleopCommandFactory> teleopCommandFactories;
    private final CommandContext commandContext;

    public TeleopCommands(CommandContext commandContext) {
        this.commandContext = commandContext;

        this.teleopCommandFactories = List.of(TeleopCommandFactory.values());
    }

    public void bindToController(TeleopController controller, ControlState controlState) {
        teleopCommandFactories.forEach(factory -> {
            controller.bindCommand(factory, factory.getBinderConsumer(commandContext),
                    controlState);
        });
    }
}
