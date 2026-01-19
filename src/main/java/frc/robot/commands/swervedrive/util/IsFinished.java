package frc.robot.commands.swervedrive.util;

public enum IsFinished {
    NO, YES;

    /**
     * @return true, if finished, false otherwise.
     */
    public boolean isFinished() {
        return equals(YES);
    }

    /**
     * @return false, if finished, true otherwise.
     */
    public boolean isUnfinished() {
        return equals(NO);
    }
}
