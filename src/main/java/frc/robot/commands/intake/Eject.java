package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;

public class Eject extends Command {
    private Intake intake;

    /**
     * Ejects the intake.
     * 
     * Ejects the intake while running, then stops the intake when done.
     * 
     * @param intake
     */
    public Eject(Intake intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    /**
     * Called repeatedly while command is running.
     * 
     * Edject the intake.
     */
    @Override
    public void execute() {
        intake.eject();
    }

    /**
     * Called when the command ends.
     * 
     * Stops the intake and indicates command end.
     */
    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }
}
