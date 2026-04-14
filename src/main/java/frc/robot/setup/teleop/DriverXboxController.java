package frc.robot.setup.teleop;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.controllable.drivebase.DriveControl;
import frc.robot.subsystems.misc.controlstate.GlobalControlState.ControlState;

class DriverXboxController implements DriverController {
    private static final Logger logger = Logger.getLogger(DriverController.class.getName());

    private final CommandXboxController xboxController = new CommandXboxController(0);

    @Override
    public void bindCommand(TeleopCommandFactory factory,
            Consumer<Consumer<Command>> binderConsumer, ControlState controlState) {
        logger.fine("Binding driver command '" + factory.toString() + "'");

        switch (factory) {
            case LOCK -> binderConsumer.accept(xboxController.leftStick()::whileTrue);            
            //case ORIENT_TO_HUB -> binderConsumer.accept(xboxController.rightTrigger()::whileTrue);

            case ZERO_GYRO_WITH_ALLIANCE -> binderConsumer.accept(xboxController.a()::onTrue);

            default -> {
            }
        }
    }

    @Override
    public DriveControl getInputDriveControl(
            Function<JoystickInputs, DriveControl> driveControlFunction) {
        return driveControlFunction.apply(
            new JoystickInputs(
                xboxController::getLeftX, 
                xboxController::getLeftY, 
                xboxController::getRightX, 
                xboxController::getRightY
            )
        );
    }
}
