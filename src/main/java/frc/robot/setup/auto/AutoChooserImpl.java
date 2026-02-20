package frc.robot.setup.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.ControlCommand;
import frc.robot.setup.teleop.CommandContext;
import frc.robot.subsystems.controllable.launcher.LauncherControl;

final class AutoChooserImpl implements AutoChooser {
    private final SendableChooser<Command> chooser;
    private final CommandContext commandContext;

    AutoChooserImpl(CommandContext commandContext) {
        this.chooser = AutoBuilder.buildAutoChooser();
        this.commandContext = commandContext;

        setupChooser();
        setupNamedCommands();
    }

    private void setupNamedCommands() {
        NamedCommands.registerCommand(
            "Named Command Test", 
            Commands.print("Named Command Test")
        );

        NamedCommands.registerCommand(
            "Shoot", 
            new ControlCommand<>(commandContext.launcher(), LauncherControl.SHOOT)
        );
    }

    private void setupChooser() {
        chooser.setDefaultOption("Do nothing", Commands.none());

        SmartDashboard.putData(chooser);
    }

    @Override
    public Command get() {
        return chooser.getSelected();
    }
    
}
