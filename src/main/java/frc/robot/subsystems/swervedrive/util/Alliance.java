package frc.robot.subsystems.swervedrive.util;

/**
 * An alliance: none, red or blue.
 */
public enum Alliance {
    NONE, RED, BLUE,;

    /**
     * Get the integer value of the alliance.
     * 
     * @return The integer value of the alliance.
     */
    public int toInt() {
        switch (this) {
            case NONE -> {
                return 0;
            }
            case RED -> {
                return 1;
            }
            case BLUE -> {
                return 2;
            }
            default -> {
                throw new IllegalStateException("Alliance should only be RED or BLUE!");
            }
        }
    }
}
