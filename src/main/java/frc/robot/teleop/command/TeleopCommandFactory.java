package frc.robot.teleop.command;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.drive.auto.RotateCCW90;
import frc.robot.commands.drive.manual.Lock;
import frc.robot.commands.drive.manual.ZeroGyro;
import frc.robot.commands.intake.LoadIntake;

public enum TeleopCommandFactory {
    /**
     * 
     */
    LOCK(context -> new Lock(context.drivebase()), Trigger::whileTrue),
    /**
     * 
     */
    ZERO_GYRO(context -> new ZeroGyro(context.drivebase()), Trigger::whileTrue),
    /**
     * 
     */
    ROTATE_90_DEG(context -> new RotateCCW90(context.drivebase()), Trigger::onTrue),
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
