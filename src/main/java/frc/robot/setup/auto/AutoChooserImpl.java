package frc.robot.setup.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.bofalib.generic.control.ControlCommand;
import frc.robot.setup.teleop.CommandContext;


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
            "Aim", //TOOD: This thing needs to turn into the old one, i dont know how to do that, make it Gian's problem
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
