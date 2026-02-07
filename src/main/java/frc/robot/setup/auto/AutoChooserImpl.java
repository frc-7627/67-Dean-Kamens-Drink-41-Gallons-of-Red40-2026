package frc.robot.setup.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.pathplanner.PathPlannerConfigException;
import frc.robot.subsystems.pathplanner.PathPlannerConfigurator;

final class AutoChooserImpl implements AutoChooser {
    private final SendableChooser<Command> chooser;

    AutoChooserImpl(PathPlannerConfigurator configurator) throws PathPlannerConfigException {
        configurator.configureAndInit();

        this.chooser = AutoBuilder.buildAutoChooser();

        setupChooser();
        setupNamedCommands();
    }

    private void setupNamedCommands() {
        NamedCommands.registerCommand(
            "Named Command Test", 
            Commands.print("Named Command Test")
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
