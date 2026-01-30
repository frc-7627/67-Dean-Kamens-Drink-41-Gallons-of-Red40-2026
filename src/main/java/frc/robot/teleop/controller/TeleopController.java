package frc.robot.teleop.controller;

import java.util.function.Consumer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.controlstate.ControlState;
import frc.robot.teleop.command.TeleopCommandFactory;

public interface TeleopController {
    void bindCommand(TeleopCommandFactory factory, Consumer<Consumer<Command>> binderConsumer,
            ControlState controlState);
}
