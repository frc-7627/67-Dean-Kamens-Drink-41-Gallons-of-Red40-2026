package frc.robot.teleop;

import java.util.Map;

public interface TeleopController {
    void bindTeleopCommands(Map<TeleopCommandId, TeleopCommand> teleopCommands,
            boolean isSimulation, boolean isTest);
}
