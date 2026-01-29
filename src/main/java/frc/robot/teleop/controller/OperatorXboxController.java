package frc.robot.teleop.controller;

import java.util.function.Consumer;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.teleop.command.TeleopCommandFactory;

public class OperatorXboxController implements TeleopController {
    private final CommandXboxController xboxController = new CommandXboxController(1);

    @Override
    public void bindCommand(TeleopCommandFactory factory, Consumer<Trigger> binder) {
        switch (factory) {
            case LOAD_INTAKE -> binder.accept(xboxController.b());
            default -> {
            }
        }
    }
}
