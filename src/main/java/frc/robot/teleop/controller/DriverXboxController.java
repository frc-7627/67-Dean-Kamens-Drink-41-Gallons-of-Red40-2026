package frc.robot.teleop.controller;

import java.util.function.Consumer;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.drivebase.InputSupplier;
import frc.robot.subsystems.legacy.SwerveSubsystem;
import frc.robot.teleop.command.TeleopCommandFactory;
import swervelib.SwerveInputStream;

public class DriverXboxController implements DriverController {
    private final CommandXboxController xboxController = new CommandXboxController(0);

    @Override
    public void bindCommand(TeleopCommandFactory factory, Consumer<Trigger> binder) {
        switch (factory) {
            case LOCK -> binder.accept(xboxController.leftBumper());
            case ZERO_GYRO -> binder.accept(xboxController.a());
            case ROTATE_90_DEG -> binder.accept(xboxController.y());
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
