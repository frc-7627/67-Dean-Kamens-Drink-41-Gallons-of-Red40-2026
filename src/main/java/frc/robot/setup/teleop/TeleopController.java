package frc.robot.setup.teleop;

import java.util.function.Consumer;
import java.util.stream.Stream;
import edu.wpi.first.wpilibj2.command.Command;
import static frc.robot.subsystems.controlstate.GlobalControlState.ControlState;

interface TeleopController {
    void bindCommand(TeleopCommandFactory factory, Consumer<Consumer<Command>> binderConsumer,
            ControlState controlState);

    default void bindAll(CommandContext context, ControlState controlState) {
        Stream.of(TeleopCommandFactory.values()).forEach(factory -> bindCommand(
            factory, 
            factory.getBinderConsumer(context), 
            controlState
        ));
    }
}
