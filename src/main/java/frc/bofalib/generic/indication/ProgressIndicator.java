package frc.bofalib.generic.indication;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface ProgressIndicator extends Subsystem {
    /**
     * Indicate the progress of a command with the current steps progressed and 
     * total number of steps.
     * 
     * @param stepsProgressed the current steps progressed
     * @param totalSteps the total number of steps
     */
    void indicateProgress(int stepsProgressed, int totalSteps);
}
