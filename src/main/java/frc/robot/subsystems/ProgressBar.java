package frc.robot.subsystems;

/**
 * A progress bar.
 */
public class ProgressBar {
    /**
     * Current number of steps in progress.
     */
    private int stepsInProgress;
    /**
     * Total number of steps for complete progress.
     */
    private int totalStepsInProgress;
    /**
     * Color of progress bar.
     */
    private String color;
    /**
     * Status of the progress bar.
     */
    private ProgressBarStatus status;

    /**
     * A progress bar status, which may be in progress, interrupted, or completed.
     */
    public enum ProgressBarStatus {
        /**
         * The progress bar is still in progress.
         */
        IN_PROGRESS,
        /**
         * The progress bar was interrupted.
         */
        INTERRUPTED,
        /**
         * The progress bar was completed.
         */
        COMPLETED,
    }

    public ProgressBar(int totalStepsInProgress, String color) {
        this.stepsInProgress = 0;
        this.totalStepsInProgress = totalStepsInProgress;
        this.color = color;
        this.status = ProgressBarStatus.IN_PROGRESS;
    }

    public int getStepsInProgress() {
        return stepsInProgress;
    }

    public int getTotalStepsInProgress() {
        return totalStepsInProgress;
    }

    public String getColor() {
        return color;
    }

    public ProgressBarStatus getStatus() {
        return status;
    }

    /**
     * Step the progress bar, if possible.
     * 
     * Will only step if the progress bar is in progress and is not full.
     * 
     * @return Whether the progress bar was changed.
     */
    public boolean stepProgressBar() {
        switch (status) {
            case IN_PROGRESS:
                if (stepsInProgress < totalStepsInProgress) {
                    stepsInProgress += 1;
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * Interrupt the progress bar, if possible.
     * 
     * Changes the status to interrupted if the progress bar is in progress.
     * 
     * @return Whether the progress bar was changed.
     */
    public boolean interrupt() {
        switch (status) {
            case IN_PROGRESS:
                status = ProgressBarStatus.INTERRUPTED;
                return true;
            default:
                return false;
        }
    }

    /**
     * Complete the progress bar, if possible.
     * 
     * Changes the status to completed if the progress bar is in progress.
     * 
     * @return Whether the progress bar was changed.
     */
    public boolean complete() {
        switch (status) {
            case IN_PROGRESS:
                status = ProgressBarStatus.COMPLETED;
                return true;
            default:
                return false;
        }
    }
}
