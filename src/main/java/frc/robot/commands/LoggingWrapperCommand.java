package frc.robot.commands;

import java.util.logging.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

public class LoggingWrapperCommand extends ParallelDeadlineGroup {
    public LoggingWrapperCommand(Command command, Class<?> cls) {
        super(command, new LoggingCommand(Logger.getLogger(cls.getSimpleName())));
    }
}
