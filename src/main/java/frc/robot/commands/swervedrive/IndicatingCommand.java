package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.Indicator;
import java.util.logging.Logger;

/**
 * A command that indicates on initialization and completion.
 */
public class IndicatingCommand extends Command {
    protected final Logger logger = Logger.getLogger(getClass().getSimpleName());
    private final Indicator indicator;

    /**
     * @param indicator The indication subsystem.
     */
    protected IndicatingCommand(Indicator indicator) {
        this.indicator = indicator;

        addRequirements(indicator);
    }

    /**
     * Called when the command is scheduled.
     * 
     * Indicates command initialization.
     */
    @Override
    public void initialize() {
        logger.info("Initializing.");
    }

    /**
     * Called when the command ends.
     * 
     * Indicates command end.
     */
    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            logger.info("Interrupted!");
            indicator.indicateInterruption();
        } else {
            logger.info("Completed.");
            indicator.indicateCompletion();
        }
    }
}
