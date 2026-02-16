package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;

public class FoldIn extends Command {
    private Intake intake;

    /**
     * Swivel the intake.
     * 
     * Swivels the intake while running, then stops the swivel when done.
     * 
     * @param intake
     */
    public FoldIn(Intake intake) {
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
        intake.foldIn();
    }

    /**
     * Called when the command ends.
     * 
     * Stops the Swivel and indicates command end.
     */
    @Override
    public void end(boolean interrupted) {
        intake.stopSwivel();
    }
}
