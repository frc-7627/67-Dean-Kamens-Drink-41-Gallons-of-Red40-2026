package frc.robot.subsystems.swervedrive.util;

/**
 * An alliance color, red or blue.
 */
public enum Alliance {
    RED, BLUE,;

    /**
     * Get the integer value of the alliance color.
     * 
     * @return The integer value of the alliance color.
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
                throw new IllegalStateException("Alliance should only be RED or BLUE!");
            }
        }
    }
}
