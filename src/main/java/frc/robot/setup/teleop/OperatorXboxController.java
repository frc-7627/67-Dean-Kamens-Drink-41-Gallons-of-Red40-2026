package frc.robot.setup.teleop;

import java.util.function.Consumer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import static frc.robot.subsystems.controlstate.GlobalControlState.ControlState;

class OperatorXboxController implements OperatorController {
    private final CommandXboxController xboxController = new CommandXboxController(1);

    @Override
    public void bindCommand(TeleopCommandFactory factory,
            Consumer<Consumer<Command>> binderConsumer, ControlState controlState) {
        switch (factory) {
            case LOAD_INTAKE -> binderConsumer.accept(xboxController.b()::whileTrue);
            case LAUNCH_FUEL -> binderConsumer.accept(xboxController.x()::whileTrue);
            default -> {
            }
        }
    }
}
