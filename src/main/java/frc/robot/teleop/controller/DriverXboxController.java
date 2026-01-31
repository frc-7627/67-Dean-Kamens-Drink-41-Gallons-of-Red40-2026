package frc.robot.teleop.controller;

import java.util.function.Consumer;
import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.controlstate.GlobalControlState.ControlState;
import frc.robot.subsystems.drivebase.InputSupplier;
import frc.robot.teleop.command.TeleopCommandFactory;

public class DriverXboxController implements DriverController {
    private final CommandXboxController xboxController = new CommandXboxController(0);

    @Override
    public void bindCommand(TeleopCommandFactory factory,
            Consumer<Consumer<Command>> binderConsumer, ControlState controlState) {
        switch (factory) {
            case LOCK -> binderConsumer.accept(xboxController.leftBumper()::whileTrue);
            case ZERO_GYRO -> binderConsumer.accept(xboxController.a()::whileTrue);
            case ROTATE_CCW_90_DEG -> binderConsumer.accept(xboxController.y()::onTrue);
            case DRIVE_ORIENTING_TO_HUB -> binderConsumer.accept(xboxController.rightBumper()::whileTrue);
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
