package frc.robot.teleop;

import java.util.List;

public interface TeleopController {
    void bindTeleopCommands(List<TeleopCommand> teleopCommands, boolean isSimulation, boolean isTest);
}
