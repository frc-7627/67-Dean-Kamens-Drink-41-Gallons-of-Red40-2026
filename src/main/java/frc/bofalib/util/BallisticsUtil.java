package frc.bofalib.util;

public final class BallisticsUtil {
    private BallisticsUtil() {}

    /**
     * @param gravityMPS2 gravitational acceleration in meters * sec^-2
     * @param horizontalM horizontal distance in meters
     * @param verticalM vertical distance in meters
     * @param pitchRad pitch angle in radians
     * @return the initial velocity required for the ballistic arc
     */
    public static double computeInitialVelocityMPS(
        double gravityMPS2,
        double horizontalM,
        double verticalM,
        double pitchRad
    ) {
        return Math.sqrt(
            0.5 * gravityMPS2 / (verticalM - horizontalM * Math.tan(pitchRad))
        ) * horizontalM / Math.cos(pitchRad);
    }

    /**
     * @param idealVelocityMPS ideal velocity in meters * sec^-1
     * @param efficiency energy efficiency, a dimensionless number in the range (0, 1]
     * @return actual velocity in meters * sec^-1
     */
    public static double computeActualVelocityMPS(
        double idealVelocityMPS,
        double efficiency
    ) {
        return Math.sqrt(
            idealVelocityMPS * idealVelocityMPS / efficiency
        );
    }

    /**
     * @param horizontalM horizontal distance in meters
     * @param verticalM vertical distance in meters
     * @param pitchRad pitch angle in radians
     * @return whether the ballistic arc is possible
     */
    public static boolean isBallisticPossible(
        double horizontalM,
        double verticalM,
        double pitchRad
    ) {
        return horizontalM * Math.tan(pitchRad) - verticalM > 0;
    }
}
