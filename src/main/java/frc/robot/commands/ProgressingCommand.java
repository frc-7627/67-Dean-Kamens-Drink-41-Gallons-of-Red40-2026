package frc.robot.commands;

import java.util.Objects;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.util.Progress;

public abstract class ProgressingCommand<CommandProgress extends Progress> extends Command {
    private CommandProgress currentProgress;

    protected ProgressingCommand(CommandProgress initialProgress) {
        this.currentProgress = Objects.requireNonNull(initialProgress);
    }

    abstract protected CommandProgress executeInStep(CommandProgress currentProgress);

    @Override
    public final void execute() {
        currentProgress = executeInStep(currentProgress);
    }

    @Override
    public final boolean isFinished() {
        return currentProgress.isFinished();
    }
}
