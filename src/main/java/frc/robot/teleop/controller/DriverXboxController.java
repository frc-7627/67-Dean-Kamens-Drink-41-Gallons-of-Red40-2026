package frc.robot.teleop.controller;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.teleop.command.TeleopCommand;
import frc.robot.teleop.command.TeleopCommandFactory;
import swervelib.SwerveInputStream;

public class DriverXboxController implements DriverController {
    private final CommandXboxController xboxController = new CommandXboxController(0);

    @Override
    public void bindCommand(TeleopCommandFactory factory, TeleopCommand command,
            ControlContext controlContext) {

        if (controlContext.isSimulation()) {
            switch (factory) {
                case RESET_ODOMETRY_START_SIM -> command.bindTrigger(xboxController.start());
                case CHARACTERIZE_DRIVE_MOTORS_SIM -> command.bindTrigger(xboxController.button(1));
                case DRIVE_ANGLE_KEYBOARD_SIM -> command.bindTrigger(xboxController.button(2));
                default -> {
                }
            }
        }

        if (controlContext.isTest()) {
            switch (factory) {
                case LOCK -> command.bindTrigger(xboxController.x());
                case ZERO_GYRO -> command.bindTrigger(xboxController.start());
                case CENTER_MODULES -> command.bindTrigger(xboxController.back());
                default -> {
                }
            }
        } else {
            switch (factory) {
                case LOCK -> command.bindTrigger(xboxController.leftBumper());
                case ZERO_GYRO -> command.bindTrigger(xboxController.a());
                case ADD_FAKE_VISION_READING -> command.bindTrigger(xboxController.x());
                case ROTATE_90_DEG -> command.bindTrigger(xboxController.y());
                default -> {
                }
            }
        }
    }

    @Override
    public TeleopDriveInputs getTeleopDriveInputs(SwerveSubsystem drivebase) {
        /**
         * Converts driver input into a field-relative ChassisSpeeds that is controlled by angular
         * velocity.
         */
        SwerveInputStream driveAngularVelocity = SwerveInputStream
                .of(drivebase.getSwerveDrive(), () -> xboxController.getLeftY() * -1,
                        () -> xboxController.getLeftX() * -1)
                .withControllerRotationAxis(xboxController::getRightX)
                .deadband(OperatorConstants.DEADBAND).scaleTranslation(0.8)
                .allianceRelativeControl(true);

        /**
         * Clone's the angular velocity input stream and converts it to a fieldRelative input
         * stream.
         */
        SwerveInputStream driveDirectAngle = driveAngularVelocity.copy()
                .withControllerHeadingAxis(xboxController::getRightX, xboxController::getRightY)
                .headingWhile(true);

        /**
         * Clone's the angular velocity input stream and converts it to a robotRelative input
         * stream.
         */
        SwerveInputStream driveRobotOriented =
                driveAngularVelocity.copy().robotRelative(true).allianceRelativeControl(false);

        SwerveInputStream driveAngularVelocityKeyboard = SwerveInputStream
                .of(drivebase.getSwerveDrive(), () -> -xboxController.getLeftY(),
                        () -> -xboxController.getLeftX())
                .withControllerRotationAxis(() -> xboxController.getRawAxis(2))
                .deadband(OperatorConstants.DEADBAND).scaleTranslation(0.8)
                .allianceRelativeControl(true);
        // Derive the heading axis with math!
        SwerveInputStream driveDirectAngleKeyboard = driveAngularVelocityKeyboard.copy()
                .withControllerHeadingAxis(
                        () -> Math.sin(xboxController.getRawAxis(2) * Math.PI) * (Math.PI * 2),
                        () -> Math.cos(xboxController.getRawAxis(2) * Math.PI) * (Math.PI * 2))
                .headingWhile(true).translationHeadingOffset(true)
                .translationHeadingOffset(Rotation2d.fromDegrees(0));

        return new TeleopDriveInputs(driveAngularVelocity, driveDirectAngle, driveRobotOriented,
                driveAngularVelocityKeyboard, driveDirectAngleKeyboard);
    }
}
