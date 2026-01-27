package frc.robot.teleop.command;

import java.util.function.BiConsumer;
import java.util.function.Function;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.intake.LoadIntake;

public enum TeleopCommandFactory {
    /**
     * 
     */
    RESET_ODOMETRY_START_SIM(
            context -> Commands.runOnce(
                    () -> context.drivebase().resetOdometry(new Pose2d(3, 3, new Rotation2d()))),
            Trigger::onTrue),
    /**
     * 
     */
    CHARACTERIZE_DRIVE_MOTORS_SIM(context -> context.drivebase().sysIdDriveMotorCommand(),
            Trigger::whileTrue),
    /**
     * 
     */
    DRIVE_ANGLE_KEYBOARD_SIM(context -> Commands.runEnd(
            () -> context.driveInputs().driveDirectAngleKeyboard().driveToPoseEnabled(true),
            () -> context.driveInputs().driveDirectAngleKeyboard().driveToPoseEnabled(false)),
            Trigger::whileTrue),
    /**
     * 
     */
    LOCK(context -> Commands.runOnce(context.drivebase()::lock, context.drivebase()).repeatedly(),
            Trigger::whileTrue),
    /**
     * 
     */
    ZERO_GYRO(context -> Commands.runOnce(context.drivebase()::zeroGyro, context.drivebase()),
            Trigger::whileTrue),
    /**
     * 
     */
    ADD_FAKE_VISION_READING(context -> Commands.runOnce(context.drivebase()::addFakeVisionReading),
            Trigger::onTrue),
    /**
     * 
     */
    CENTER_MODULES(context -> context.drivebase().centerModulesCommand(), Trigger::whileTrue),
    /**
     * 
     */
    ROTATE_90_DEG(
            context -> Commands.runOnce(() -> context.drivebase()
                    .driveToPose(context.drivebase().getPose().rotateBy(Rotation2d.kCCW_90deg))),
            Trigger::onTrue),
    /**
     * 
     */
    LOAD_INTAKE(context -> new LoadIntake(context.indicator(), context.intake()),
            Trigger::whileTrue)

    ;

    private final Function<CommandContext, Command> commandSupplier;

    private final BiConsumer<Trigger, Command> binder;

    TeleopCommandFactory(Function<CommandContext, Command> commandSupplier,
            BiConsumer<Trigger, Command> binder) {
        this.commandSupplier = commandSupplier;
        this.binder = binder;
    }

    public TeleopCommand makeTeleopCommand(CommandContext context) {
        return new TeleopCommand(commandSupplier.apply(context), binder);
    }
}
