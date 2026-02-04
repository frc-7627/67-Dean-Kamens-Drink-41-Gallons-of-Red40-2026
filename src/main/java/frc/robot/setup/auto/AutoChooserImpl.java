package frc.robot.setup.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.resources.pathplanner.PathPlannerConfigException;
import frc.robot.resources.pathplanner.PathPlannerConfigurator;
import frc.robotlib.dashboard.fields.SubdashboardBase;

final class AutoChooserImpl extends SubdashboardBase implements AutoChooser {
    private static final String FIELD_NAME = "Auto Chooser";

    private final SendableChooser<Command> chooser;

    AutoChooserImpl(PathPlannerConfigurator configurator) throws PathPlannerConfigException {
        super(FIELD_NAME);

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
    public boolean checkPulled(Command pulled) {
        return true;
    }

    @Override
    public Command getPulled() {
        return chooser.getSelected();
    }
    
}
