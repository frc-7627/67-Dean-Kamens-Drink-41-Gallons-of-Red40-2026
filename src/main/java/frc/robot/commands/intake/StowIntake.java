package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;

public class StowIntake extends Command {
    private Intake intake;

    /**
     * Loads the intake.
     * 
     * Loads the intake while running, then stops the intake when done.
     * 
     * @param intake
     */
    public StowIntake(Intake intake) {
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
        intake.stop();
    }
}
