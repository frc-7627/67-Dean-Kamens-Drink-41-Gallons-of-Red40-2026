package frc.robot.setup.teleop;

import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.logging.Logger;
import com.pathplanner.lib.auto.AutoBuilder.TriFunction;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.controlstate.GlobalControlState.ControlState;
import frc.robot.subsystems.drivebase.DriveControl;

class DriverXboxController implements DriverController {
    private static final Logger logger = Logger.getLogger(DriverController.class.getName());

    private final CommandXboxController xboxController = new CommandXboxController(0);

    @Override
    public void bindCommand(TeleopCommandFactory factory,
            Consumer<Consumer<Command>> binderConsumer, ControlState controlState) {
        logger.fine("Binding driver command '" + factory.toString() + "'");

        switch (factory) {
            case LOCK -> binderConsumer.accept(xboxController.leftBumper()::whileTrue);
            case DRIVE_WHILE_ORIENTING_TO_HUB -> binderConsumer.accept(xboxController.rightBumper()::whileTrue);
            
            case ORIENT_TO_HUB -> binderConsumer.accept(xboxController.rightTrigger()::whileTrue);

            case ZERO_GYRO -> binderConsumer.accept(xboxController.a()::whileTrue);
            case FEED -> binderConsumer.accept(xboxController.b()::whileTrue);
            case SHOOT -> binderConsumer.accept(xboxController.x()::whileTrue);
            case FEED_AND_SHOOT -> binderConsumer.accept(xboxController.y()::whileTrue);

            default -> {
            }
        }
    }

    @Override
    public DriveControl getInputDriveControl(
        TriFunction<
            DoubleSupplier,
            DoubleSupplier,
            DoubleSupplier,
            DriveControl
        > driveControlFactory
    ) {
        return driveControlFactory.apply(
            () -> xboxController.getLeftY() * -1,
            () -> xboxController.getLeftX() * -1, 
            xboxController::getRightX
        );
    }
}
