package frc.robot.setup.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.bofalib.dashboard.PullingField;
import frc.robot.resources.pathplanner.PathPlannerConfigException;
import frc.robot.resources.pathplanner.PathPlannerConfigurator;

/**
 * Interface for selecting the autonomous commands
 */
public interface AutoChooser extends PullingField<Command> {
    static AutoChooser create(
            PathPlannerConfigurator configurator) throws PathPlannerConfigException {
        return new AutoChooserImpl(configurator);
    }
}
