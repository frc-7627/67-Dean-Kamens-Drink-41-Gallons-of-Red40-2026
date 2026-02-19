package frc.robot.setup.teleop;

import java.util.function.Consumer;
import java.util.logging.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.misc.controlstate.GlobalControlState.ControlState;

class OperatorXboxController implements OperatorController {
    private static final Logger logger = Logger.getLogger(OperatorController.class.getName());

    private final CommandXboxController xboxController = new CommandXboxController(1);

    @Override
    public void bindCommand(TeleopCommandFactory factory,
            Consumer<Consumer<Command>> binderConsumer, ControlState controlState) {
        logger.fine("Binding operator command '" + factory.toString() + "'");

        switch (factory) {
            case LOAD_INTAKE -> binderConsumer.accept(xboxController.leftTrigger()::whileTrue);
            case EJECT_INTAKE -> binderConsumer.accept(xboxController.leftBumper()::whileTrue);
            case STOW_INTAKE -> binderConsumer.accept(xboxController.x()::whileTrue);
            case FEED_AND_SHOOT -> binderConsumer.accept(xboxController.rightTrigger()::whileTrue);
            case AGITATE -> binderConsumer.accept(xboxController.a()::whileTrue);
            case SHOOT -> binderConsumer.accept(xboxController.rightStick()::whileTrue);
            case SWIVEL_OUT-> binderConsumer.accept(xboxController.b()::whileTrue);
            case FEED -> binderConsumer.accept(xboxController.leftStick()::whileTrue);
            default -> {
            }
        }
    }
}
