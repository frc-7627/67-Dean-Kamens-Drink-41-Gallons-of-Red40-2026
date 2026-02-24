package frc.bofalib.generic.indication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.wpi.first.wpilibj2.command.Command;

public final class ProgressingCommandBuilder {
    private final ProgressIndicator indicator;
    private final List<Command> commands;
    private final List<String> stepNames;

    public ProgressingCommandBuilder(
        ProgressIndicator indicator
    ) {
        this.indicator = Objects.requireNonNull(indicator);
        this.commands = new ArrayList<>();
        this.stepNames = new ArrayList<>();
    }

    public ProgressingCommandBuilder addStep(
        String stepName,
        Command command
    ) {
        this.stepNames.add(stepName);
        this.commands.add(command);

        return this;
    }

    public Command build() {
        return new ProgressingCommand(indicator, commands, stepNames);
    }
}
