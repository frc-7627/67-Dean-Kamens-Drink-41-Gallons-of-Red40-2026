package frc.robot.setup.auto;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * Interface for selecting the autonomous commands
 */
public interface AutoChooser extends Supplier<Command> {
    static AutoChooser create() {
        return new AutoChooserImpl();
    }
}
