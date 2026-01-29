package frc.robot.teleop.command;

import java.util.List;
import frc.robot.subsystems.Indicator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.subsystems.legacy.SwerveSubsystem;
import frc.robot.teleop.controller.TeleopController;

public class TeleopCommands {
    private final List<TeleopCommandFactory> teleopCommandFactories;
    private final CommandContext commandContext;

    public TeleopCommands(Indicator indicator, Drivebase drivebase, Intake intake) {
        this.commandContext = new CommandContext(indicator, drivebase, intake);

        this.teleopCommandFactories = List.of(TeleopCommandFactory.values());
    }

    public void bindToController(TeleopController controller) {
        teleopCommandFactories.forEach(factory -> {
            controller.bindCommand(factory, factory.getBinder(commandContext));
        });
    }
}
