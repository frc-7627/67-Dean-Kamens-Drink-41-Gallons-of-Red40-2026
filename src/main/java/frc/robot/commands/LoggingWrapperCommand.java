package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class LoggingWrapperCommand extends LoggingCommand {
    private final Command command;

    public LoggingWrapperCommand(Command command) {
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
