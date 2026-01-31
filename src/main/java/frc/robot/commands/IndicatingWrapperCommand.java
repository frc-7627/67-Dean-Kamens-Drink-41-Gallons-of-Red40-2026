package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WrapperCommand;
import frc.robot.subsystems.indication.Indicator;

public class IndicatingWrapperCommand extends WrapperCommand {
    private final Indicator indicator;

    public IndicatingWrapperCommand(Command command, Indicator indicator) {
        super(command);

        this.indicator = indicator;
    }

    @Override
    public void initialize() {
        super.initialize();
        indicator.indicateInit();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);

        if (interrupted) {
            indicator.indicateInterruption();
        } else {
            indicator.indicateCompletion();
        }
    }
}
