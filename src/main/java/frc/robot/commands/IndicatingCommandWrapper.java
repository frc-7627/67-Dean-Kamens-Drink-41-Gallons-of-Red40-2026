package frc.robot.commands;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indicator;

public class IndicatingCommandWrapper extends IndicatingCommand {
    private final Command command;

    public IndicatingCommandWrapper(Command command, Indicator indicator) {
        super(Logger.getLogger(command.getClass().getSimpleName()), indicator);
        this.command = command;
    }

    @Override
    public void initialize() {
        super.initialize();
        command.initialize();
    }

    @Override
    public void execute() {
        super.execute();
        command.execute();
    }

    @Override
    public boolean isFinished() {
        return command.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        command.end(interrupted);
    }
}
