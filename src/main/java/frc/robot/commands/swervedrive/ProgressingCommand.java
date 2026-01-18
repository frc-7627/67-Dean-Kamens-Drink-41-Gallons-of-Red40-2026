package frc.robot.commands.swervedrive;

import frc.robot.commands.swervedrive.IndicatingCommand;
import frc.robot.commands.swervedrive.util.IsFinished;
import frc.robot.commands.swervedrive.util.Progress;
import frc.robot.subsystems.swervedrive.Indicator;

public class ProgressingCommand<CommandProgress extends Progress> extends IndicatingCommand {
    private final CommandProgress commandProgress;

    protected ProgressingCommand(Indicator indicator, CommandProgress commandProgress) {
        super(indicator);

        this.commandProgress = commandProgress;
    }

    protected void transition() {
        commandProgress.transition();
    } 
}
