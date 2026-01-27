package frc.robot.teleop.controller;

import frc.robot.teleop.command.TeleopCommand;
import frc.robot.teleop.command.TeleopCommandFactory;

public interface TeleopController {
    void bindCommand(TeleopCommandFactory factory, TeleopCommand command,
            ControlContext controlContext);
}
