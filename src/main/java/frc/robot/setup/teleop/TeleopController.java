package frc.robot.setup.teleop;

import static frc.robot.subsystems.misc.controlstate.GlobalControlState.ControlState;
import java.util.function.Consumer;
import java.util.stream.Stream;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * Interface that binds teleop controls to commands to be done by the robot.
 */
interface TeleopController {
    void bindCommand(TeleopCommandFactory factory, Consumer<Consumer<Command>> binderConsumer,
            ControlState controlState);

    default void bindAll(CommandContext context, ControlState controlState) {
        Stream.of(TeleopCommandFactory.values()).forEach(factory -> bindCommand(
                factory,
                factory.getBinderConsumer(context),
                controlState));
    }
}
