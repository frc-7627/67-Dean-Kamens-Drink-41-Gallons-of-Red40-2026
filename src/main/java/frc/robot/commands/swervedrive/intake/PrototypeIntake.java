package frc.robot.commands.swervedrive.intake;

import frc.robot.commands.swervedrive.IndicatingCommand;
import frc.robot.subsystems.swervedrive.Indicator;
import frc.robot.subsystems.swervedrive.Intake;

public class PrototypeIntake extends IndicatingCommand {
    private Intake intake;
    
    /**
     * Runs the prototype intake.
     * 
     * Loads the intake while running, then stops the intake when done.
     * 
     * @param indicator
     * @param intake
     */
    public PrototypeIntake(Indicator indicator, Intake intake) {
        super(indicator);

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
