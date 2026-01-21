package frc.robot.subsystems.swervedrive.util;

enum ValueMode {
    PUSH_ONLY, PUSH_AND_PULL_CONST, PUSH_AND_PULL;

    /**
     * @return Whether the mode allows pulling.
     */
    boolean isPull() {
        return switch (this) {
            case PUSH_ONLY -> false;
            case PUSH_AND_PULL_CONST -> true;
            case PUSH_AND_PULL -> true;
        };
    }
}
