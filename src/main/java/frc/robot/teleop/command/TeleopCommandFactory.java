package frc.robot.teleop.command;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.intake.LoadIntake;

public enum TeleopCommandFactory {
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

    Consumer<Trigger> getBinder(CommandContext context) {
        return trigger -> binder.accept(trigger, commandSupplier.apply(context));
    }
}
