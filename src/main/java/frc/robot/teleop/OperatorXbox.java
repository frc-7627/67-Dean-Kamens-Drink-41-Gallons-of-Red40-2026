package frc.robot.teleop;

import java.util.Map;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class OperatorXbox implements TeleopController {
    private final CommandXboxController xboxController = new CommandXboxController(1);

    @Override
    public void bindTeleopCommands(Map<TeleopCommandId, TeleopCommand> teleopCommands,
            boolean isSimulation, boolean isTest) {
        // TODO
    }
}
