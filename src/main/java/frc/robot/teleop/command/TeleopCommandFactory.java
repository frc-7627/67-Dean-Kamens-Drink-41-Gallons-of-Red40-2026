package frc.robot.teleop.command;

import java.util.function.Consumer;
import java.util.function.Function;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.commands.control.ToggleControlState;
import frc.robot.commands.drive.auto.RotateCCW90;
import frc.robot.commands.drive.teleop.Lock;
import frc.robot.commands.drive.teleop.ZeroGyro;
import frc.robot.commands.intake.LoadIntake;

public enum TeleopCommandFactory {
    /**
     * 
     */
    LOCK(context -> new Lock(context.drivebase())),
    /**
     * 
     */
    ZERO_GYRO(context -> new ZeroGyro(context.drivebase())),
    /**
     * 
     */
    ROTATE_90_DEG(context -> new RotateCCW90(context.drivebase())),
    /**
     * 
     */
    LOAD_INTAKE(context -> new LoadIntake(context.indicator(), context.intake())),
    /**
     * 
     */
    LAUNCH_FUEL(context -> new PrintCommand("launch fuel")),

    TOGGLE_CONTROL_STATE(context -> new ToggleControlState(context.controlStateToggler())),

    ;

    private final Function<CommandContext, Command> commandSupplier;

    private TeleopCommandFactory(Function<CommandContext, Command> commandSupplier) {
        this.commandSupplier = commandSupplier;
    }

    Consumer<Consumer<Command>> getBinderConsumer(CommandContext context) {
        return binder -> binder.accept(commandSupplier.apply(context));
    }
}
