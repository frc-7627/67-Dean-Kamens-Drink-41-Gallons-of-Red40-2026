package frc.robot.commands;

import java.util.logging.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WrapperCommand;

public class LoggingWrapperCommand extends WrapperCommand {
    private final Logger logger;

    public LoggingWrapperCommand(Command command) {
        super(command);

        this.logger = Logger.getLogger(command.getClass().getName());
    }

    @Override
    public void initialize() {
        super.initialize();
        logger.info("Initialized.");
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);

        if (interrupted) {
            logger.info("Interrupted!");
        } else {
            logger.info("Completed.");
        }
    }
}
