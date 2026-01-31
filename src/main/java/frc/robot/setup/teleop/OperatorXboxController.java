package frc.robot.setup.teleop;

import java.util.function.Consumer;
import java.util.logging.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import static frc.robot.subsystems.controlstate.GlobalControlState.ControlState;

class OperatorXboxController implements OperatorController {
    private static final Logger logger = Logger.getLogger(OperatorController.class.getSimpleName());

    private final CommandXboxController xboxController = new CommandXboxController(1);

    @Override
    public void bindCommand(TeleopCommandFactory factory,
            Consumer<Consumer<Command>> binderConsumer, ControlState controlState) {
        logger.fine("Binding operator command...");
        
        switch (factory) {
            case LOAD_INTAKE -> binderConsumer.accept(xboxController.b()::whileTrue);
            case LAUNCH_FUEL -> binderConsumer.accept(xboxController.x()::whileTrue);
            default -> {
            }
        }
    }
}
