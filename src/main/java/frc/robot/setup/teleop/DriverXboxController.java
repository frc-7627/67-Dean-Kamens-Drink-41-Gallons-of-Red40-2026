package frc.robot.setup.teleop;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.controlstate.GlobalControlState.ControlState;
import frc.robot.subsystems.drivebase.InputSupplier;

class DriverXboxController implements DriverController {
    private static final Logger logger = Logger.getLogger(DriverController.class.getName());

    private final CommandXboxController xboxController = new CommandXboxController(0);

    @Override
    public void bindCommand(TeleopCommandFactory factory,
            Consumer<Consumer<Command>> binderConsumer, ControlState controlState) {
        logger.fine("Binding driver command '" + factory.toString() + "'");

        switch (factory) {
            case LOCK -> binderConsumer.accept(xboxController.leftBumper()::whileTrue);
            case ZERO_GYRO -> binderConsumer.accept(xboxController.a()::whileTrue);
            case ROTATE_CCW_90_DEG -> binderConsumer.accept(xboxController.y()::whileTrue);
            case DRIVE_WHILE_ORIENTING_TO_HUB -> binderConsumer.accept(xboxController.rightBumper()::whileTrue);
            case ORIENT_TO_HUB -> binderConsumer.accept(xboxController.rightTrigger()::whileTrue);
            default -> {
            }
        }
    }

    @Override
    public Supplier<ChassisSpeeds> getInput(InputSupplier drivebase) {
        return drivebase.getInput(() -> xboxController.getLeftY() * -1,
                () -> xboxController.getLeftX() * -1, xboxController::getRightX);
    }
}
