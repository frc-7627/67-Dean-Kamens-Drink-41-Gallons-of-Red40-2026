package frc.robot.subsystems.swervedrive.util;

/**
 * An alliance: none, red or blue.
 */
public enum Alliance {
    RED, BLUE;

    /**
     * Get the integer value of the alliance.
     * 
     * @return The integer value of the alliance.
     */
    public int toInt() {
        switch (this) {
            case RED -> {
                return 0;
            }
            case BLUE -> {
                return 1;
            }
            default -> {
                // Never happens.
                throw new IllegalStateException("Alliance should only be RED(0) or BLUE(1)!");
            }
        }
    }
}
