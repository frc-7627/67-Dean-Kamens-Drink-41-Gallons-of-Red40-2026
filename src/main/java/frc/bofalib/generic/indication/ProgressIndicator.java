package frc.bofalib.generic.indication;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface ProgressIndicator extends Subsystem {
    /**
     * Indicate the progress of a command with the current progress.
     * 
     * @param <CommandProgress> an amount of progress.
     * @param currentProgress   the current progress.
     */
    <CommandProgress extends Progress> void indicateProgress(CommandProgress currentProgress);

    void indicateProgress(int stepsProgressed, int totalSteps);
}
