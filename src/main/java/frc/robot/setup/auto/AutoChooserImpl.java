package frc.robot.setup.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.bofalib.generic.control.ControlCommand;
import frc.robot.commands.Score;
import frc.robot.setup.teleop.CommandContext;
import frc.robot.subsystems.controllable.intake.IntakeControl;
import frc.robot.subsystems.controllable.launcher.LauncherControlSimple;
import frc.robot.subsystems.controllable.swivel.SwivelControl;

final class AutoChooserImpl implements AutoChooser {
    private final SendableChooser<Command> chooser;
    private final CommandContext commandContext;

    AutoChooserImpl(CommandContext commandContext) {
        this.commandContext = commandContext;
        
        setupNamedCommands();

        this.chooser = AutoBuilder.buildAutoChooser();
        setupChooser();
    }

    private void setupNamedCommands() {
        NamedCommands.registerCommand(
            "Named Command Test", 
            Commands.print("Named Command Test")
        );

        NamedCommands.registerCommand(
            "Aim", 
                new ControlCommand<>(commandContext.drivebase(),
                    commandContext.inputDriveControl().withRotationControl(
                    commandContext.drivebase().getAngularDriveControl(
                    commandContext.drivebase().getLocationSupplierAngleTargetter( () ->
                    commandContext.gameInfoSupplier().getHubPosition()
                            )
                        )
                    )
                )
            );

        NamedCommands.registerCommand(
            "Score", 
            new Score(commandContext.gameInfoSupplier(), commandContext.indicator(), 
                commandContext.drivebase(), commandContext.launcher(), commandContext.agitator(), commandContext.feeder())
                .alongWith(new ControlCommand<>(commandContext.intake(), IntakeControl.LOAD))
        );

        NamedCommands.registerCommand(
            "Score with Rock", 
            new Score(commandContext.gameInfoSupplier(), commandContext.indicator(), 
                commandContext.drivebase(), commandContext.launcher(), commandContext.agitator(), commandContext.feeder())
                .alongWith( new ControlCommand<>(commandContext.swivel(), SwivelControl.FOLD_IN))
                .alongWith(new ControlCommand<>(commandContext.intake(), IntakeControl.LOAD))
        );

        NamedCommands.registerCommand(
            "Fold Out", 
            new ControlCommand<>(commandContext.swivel(), SwivelControl.FOLD_OUT)
        );

        NamedCommands.registerCommand("Intake", 
        new ControlCommand<>(commandContext.intake(), IntakeControl.LOAD));
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
