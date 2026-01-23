package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class LoadIntake extends Command {
    private Intake intake;

    public LoadIntake(Intake intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    /**
     * Initialize the command.
     * 
     * Indicates initialization.
     */
    @Override
    public void initialize() {
        // TODO: indicate initialization
    }

    /**
     * Executes the command.
     * 
     * Loads the intake.
     */
    @Override
    public void execute() {
        intake.load();
    }

    /**
     * Ends the command.
     * 
     * Stops the intake and indicates command end.
     */
    @Override
    public void end(boolean interrupted) {
        intake.stop();
        // TODO: indicate end of command.
    }
}
