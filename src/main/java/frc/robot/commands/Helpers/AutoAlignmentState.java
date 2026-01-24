package frc.robot.commands.Helpers;

import frc.robot.commands.util.Progress;

public enum AutoAlignmentState implements Progress {
    LOOKING_FOR_TARGET,
    TARGET_FOUND_DRIVING,
    IN_POSITION;

    public int getStepsProgressed() {
        return this.ordinal() + 1;
    }

    @Override
    public int getTotalSteps() {
        return 3;
    }
}
