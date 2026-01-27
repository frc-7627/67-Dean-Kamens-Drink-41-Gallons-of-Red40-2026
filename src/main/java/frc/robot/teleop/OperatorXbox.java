package frc.robot.teleop;

import java.util.List;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class OperatorXbox implements TeleopController {
    private final CommandXboxController xboxController = new CommandXboxController(1);

    @Override
    public void bindTeleopCommands(List<TeleopCommand> teleopCommands, boolean isSimulation, boolean isTest) {
        // TODO
    }
}
