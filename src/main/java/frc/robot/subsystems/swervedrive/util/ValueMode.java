package frc.robot.subsystems.swervedrive.util;

public enum ValueMode {
    PUSH_ONLY, PUSH_AND_PULL_CONST, PUSH_AND_PULL;

    /**
     * @return Whether the mode allows pulling.
     */
    public boolean isPull() {
        return switch (this) {
            case PUSH_ONLY -> false;
            case PUSH_AND_PULL_CONST -> true;
            case PUSH_AND_PULL -> true;
        };
    }
}
