package frc.robot.commands.swervedrive.intake;

import java.util.logging.Logger;
import frc.robot.commands.swervedrive.IndicatingCommand;
import frc.robot.subsystems.swervedrive.Indicator;
import frc.robot.subsystems.swervedrive.Intake;

public class LoadIntake extends IndicatingCommand {
    private static final Logger LOGGER = Logger.getLogger(LoadIntake.class.getSimpleName());
    private Intake intake;

    /**
     * Loads the intake.
     * 
     * Loads the intake while running, then stops the intake when done.
     * 
     * @param indicator
     * @param intake
     */
    public LoadIntake(Indicator indicator, Intake intake) {
        super(LOGGER, indicator);

        this.intake = intake;

        addRequirements(intake);
    }

    /**
     * Called repeatedly while command is running.
     * 
     * Loads the intake.
     */
    @Override
    public void execute() {
        intake.load();
    }

    /**
     * Called when the command ends.
     * 
     * Stops the intake and indicates command end.
     */
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        intake.stop();
    }
}
