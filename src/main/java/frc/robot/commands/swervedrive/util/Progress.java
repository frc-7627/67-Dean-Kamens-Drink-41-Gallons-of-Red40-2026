package frc.robot.commands.swervedrive.util;

/**
 * Interface for classes which implement a progress-like behavior.
 */
public interface Progress {
    /**
     * @return The number of steps progressed.
     */
    int getStepsProgressed();

    /**
     * @return The total number of steps.
     */
    int getTotalSteps();

    /**
     * @return Whether the progress is finished.
     */
    default IsFinished isFinished() {
        if (getStepsProgressed() >= getTotalSteps()) {
            return IsFinished.YES;
        } else {
            return IsFinished.NO;
        }
    }
}
