package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.indication.Indicator;
import java.util.logging.Logger;

/**
 * A command that indicates on initialization and completion.
 */
public class IndicatingCommand extends Command {
    protected final Logger logger;
    protected final Indicator indicator;

    /**
     * @param logger the logger to use.
     * @param indicator the indication subsystem.
     */
    protected IndicatingCommand(Logger logger, Indicator indicator) {
        this.logger = logger;
        this.indicator = indicator;

        addRequirements(indicator);
    }

    /**
     * Called when the command is scheduled. Indicates command initialization.
     */
    @Override
    public void initialize() {
        indicator.indicateInit(logger);
    }

    /**
     * Called when the command ends. Indicates command end.
     * 
     * Indicates interruption or completion depending on how the command ended.
     */
    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            indicator.indicateInterruption(logger);
        } else {
            indicator.indicateCompletion(logger);
        }
    }
}
