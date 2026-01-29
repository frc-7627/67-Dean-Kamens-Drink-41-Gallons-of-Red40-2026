package frc.robot.teleop.controller;

import java.util.function.Consumer;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.teleop.command.TeleopCommandFactory;

public interface TeleopController {
    void bindCommand(TeleopCommandFactory factory, Consumer<Trigger> binder);
}
