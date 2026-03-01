package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;

public class FoldOut extends Command {
    private Intake intake;

    /**
     * Swivels the intake.
     * 
     * Swivels the intake while running, then stops the Swivels when done.
     * 
     * @param intake
     */
    public FoldOut(Intake intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    /**
     * Called repeatedly while command is running.
     * 
     * Swivels the intake.
     */
    @Override
    public void execute() {
        intake.foldOut();
    }

    /**
     * Called when the command ends.
     * 
     * Stops the Swivels and indicates command end.
     */
    @Override
    public void end(boolean interrupted) {
        intake.stopSwivel();
    }
}
