package frc.robot.subsystems.swervedrive.util;

/**
 * A game phase.
 */
public enum Phase {
    AUTO,
    TRANSITION,
    TELEOP_1,
    TELEOP_2,
    TELEOP_3,
    TELEOP_4,
    ENDGAME;

    /**
     * Get the integer value of the phase.
     * @return The integer value of the phase.
     */
    public int toInt() {
        switch (this) {
            case AUTO -> {
                return 0;
            }
            case TRANSITION -> {
                return 1;
            }
            case TELEOP_1 -> {
                return 2;
            }
            case TELEOP_2 -> {
                return 3;
            }
            case TELEOP_3 -> {
                return 4;
            }
            case TELEOP_4 -> {
                return 5;
            }
            case ENDGAME -> {
                return 6;
            }
            default -> {
                throw new IllegalStateException("Phase should be between AUTO(0) and ENDGAME(6)!");
            }
        }
    }
}
