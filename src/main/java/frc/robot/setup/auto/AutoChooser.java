package frc.robot.setup.auto;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.pathplanner.PathPlannerConfigException;
import frc.robot.subsystems.pathplanner.PathPlannerConfigurator;

/**
 * Interface for selecting the autonomous commands
 */
public interface AutoChooser extends Supplier<Command> {
    static AutoChooser create(
            PathPlannerConfigurator configurator) throws PathPlannerConfigException {
        return new AutoChooserImpl(configurator);
    }
}
