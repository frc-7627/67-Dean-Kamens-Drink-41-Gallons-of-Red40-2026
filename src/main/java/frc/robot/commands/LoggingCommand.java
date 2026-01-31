package frc.robot.commands;

import java.util.logging.Logger;
import edu.wpi.first.wpilibj2.command.Command;

public class LoggingCommand extends Command {
    private final Logger logger;

    protected LoggingCommand(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void initialize() {
        logger.info("Initialized.");
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            logger.info("Interrupted!");
        } else {
            logger.info("Completed.");
        }
    }
}
