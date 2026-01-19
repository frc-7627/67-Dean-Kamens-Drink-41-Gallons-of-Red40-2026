package frc.robot.commands.swervedrive;

import frc.robot.commands.swervedrive.util.Progress;
import frc.robot.subsystems.swervedrive.Indicator;

public class ProgressingCommand<CommandProgress extends Progress> extends IndicatingCommand {
    private CommandProgress commandProgress;

    protected ProgressingCommand(Indicator indicator, CommandProgress commandProgress) {
        super(indicator);

        this.commandProgress = commandProgress;
    }

    protected CommandProgress getProgress() {
        return commandProgress;
    }

    protected void setProgress(CommandProgress commandProgress) {
        this.commandProgress = commandProgress;
        logger.fine(String.format("Now on step: \"{0}\". ({1}/{2})", commandProgress,
                commandProgress.getStepsProgressed(), commandProgress.getTotalSteps()));
        indicator.indicateProgress(commandProgress.getStepsProgressed(),
                commandProgress.getTotalSteps());
    }
}
