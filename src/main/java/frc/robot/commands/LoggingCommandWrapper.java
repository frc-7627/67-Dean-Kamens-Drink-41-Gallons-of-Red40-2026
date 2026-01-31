package frc.robot.commands;

import java.util.logging.Logger;
import edu.wpi.first.wpilibj2.command.Command;

public class LoggingCommandWrapper extends LoggingCommand {
    private final Command command;

    public LoggingCommandWrapper(Command command) {
        super(Logger.getLogger(command.getClass().getSimpleName()));

        this.command = command;
    }

    @Override
    public void initialize() {
        super.initialize();
        command.initialize();
    }

    @Override
    public void execute() {
        command.execute();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        command.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return command.isFinished();
    }
}
