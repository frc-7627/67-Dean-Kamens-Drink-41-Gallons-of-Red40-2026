package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.resources.pathplanner.PathPlannerConfigException;
import frc.robot.resources.pathplanner.PathPlannerConfigurator;
import frc.robotlib.resource.dashboard.PullingField;

public interface AutoChooser extends PullingField<Command> {
    static AutoChooser create(
        PathPlannerConfigurator configurator
    ) throws PathPlannerConfigException {
        return new AutoChooserImpl(configurator);
    }
}
