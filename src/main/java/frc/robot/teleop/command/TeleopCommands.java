package frc.robot.teleop.command;

import java.util.List;
import frc.robot.subsystems.Indicator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.teleop.controller.ControlContext;
import frc.robot.teleop.controller.TeleopController;
import frc.robot.teleop.controller.TeleopDriveInputs;

public class TeleopCommands {
    private final List<TeleopCommandFactory> teleopCommandFactories;
    private final CommandContext commandContext;

    public TeleopCommands(Indicator indicator, SwerveSubsystem drivebase, Intake intake,
            TeleopDriveInputs driveInputs) {
        this.commandContext = new CommandContext(indicator, drivebase, intake, driveInputs);

        this.teleopCommandFactories = List.of(TeleopCommandFactory.values());
    }

    public void bindToController(TeleopController teleopController, ControlContext controlContext) {
        teleopCommandFactories.forEach(factory -> {
            teleopController.bindCommand(factory, factory.makeTeleopCommand(commandContext),
                    controlContext);;
        });
    }
}
