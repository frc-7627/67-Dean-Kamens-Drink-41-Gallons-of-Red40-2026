package frc.bofalib.generic.indication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

final class ProgressingCommand extends Command {
    private final ProgressIndicator indicator;

    private final List<Command> commands;
    private Iterator<Command> commandIter;

    private Optional<Command> commandOptional = Optional.empty();
    private OptionalInt indexOptional = OptionalInt.empty();
    private boolean runsWhenDisabled = true;
    private InterruptionBehavior interruptionBehavior = InterruptionBehavior.kCancelIncoming;

    ProgressingCommand(ProgressIndicator indicator, Collection<? extends Command> commands) {
        this.indicator = Objects.requireNonNull(indicator);

        Objects.requireNonNull(commands);
        this.commands = new ArrayList<>(commands.size());

        CommandScheduler.getInstance().registerComposedCommands(
            commands.toArray(Command[]::new)
        );

        commands.forEach(command -> {
            Objects.requireNonNull(command);
            this.commands.add(command);
            
            addRequirements(command.getRequirements());

            this.runsWhenDisabled &= command.runsWhenDisabled();

            if (command.getInterruptionBehavior().equals(InterruptionBehavior.kCancelSelf)) {
                interruptionBehavior = InterruptionBehavior.kCancelSelf;
            }
        });
    }

    private void step() {
        commandOptional.ifPresent(command -> {
            command.end(true);
        });

        if (commandIter.hasNext()) {
            final Command nextCommand = commandIter.next();

            nextCommand.initialize();

            commandOptional = Optional.of(nextCommand);

            final int index = 
                indexOptional.isPresent() ? indexOptional.getAsInt() + 1 : 0
            ;

            indicator.indicateProgress(index, commands.size() - 1);

            indexOptional = OptionalInt.of(index);
        } else {
            commandOptional = Optional.empty();
            indexOptional = OptionalInt.empty();
        }
    }

    @Override
    public void initialize() {
        commandIter = commands.iterator();

        step();
    }

    @Override
    public void execute() {
        commandOptional.ifPresent(command -> {
            command.execute();

            if (command.isFinished()) {
                step();
            }
        });
    }

    @Override
    public boolean isFinished() {
        return !commandIter.hasNext();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            commandOptional.ifPresent(command -> command.end(true));
        }

        commandOptional = Optional.empty();
    }

    @Override
    public boolean runsWhenDisabled() {
        return runsWhenDisabled;
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return interruptionBehavior;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);

        builder.addIntegerProperty("index", () -> indexOptional.orElse(-1), null);
    }
}
