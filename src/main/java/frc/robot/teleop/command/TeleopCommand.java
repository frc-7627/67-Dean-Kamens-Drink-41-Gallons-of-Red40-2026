package frc.robot.teleop.command;

import java.util.function.BiConsumer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * A teleop command.
 */
public class TeleopCommand {

    private final Command command;

    private final BiConsumer<Trigger, Command> binder;

    public TeleopCommand(Command command, BiConsumer<Trigger, Command> binder) {
        this.command = command;
        this.binder = binder;
    }

    public void bindTrigger(Trigger trigger) {
        binder.accept(trigger, command);;
    }
}
