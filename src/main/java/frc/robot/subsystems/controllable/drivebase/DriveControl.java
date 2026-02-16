package frc.robot.subsystems.controllable.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;

public interface DriveControl {
    /**
     * Initialize.
     */
    default void initialize() {}

    /**
     * @return the target speeds
     */
    ChassisSpeeds getSpeeds();

    /**
     * @param rotationControl the provided strategy for rotation
     * @return a drive control strategy using this strategy for translation and the provided
     *         strategy for rotation
     */
    default DriveControl withRotationControl(DriveControl rotationControl) {
        final DriveControl translationControl = this;

        return new DriveControl() {
            private final ChassisSpeeds workingSpeeds = new ChassisSpeeds();

            @Override
            public void initialize() {
                translationControl.initialize();
                rotationControl.initialize();
            }

            @Override
            public ChassisSpeeds getSpeeds() {
                final ChassisSpeeds translationSpeeds = translationControl.getSpeeds();
                final ChassisSpeeds rotationSpeeds = rotationControl.getSpeeds();

                workingSpeeds.vxMetersPerSecond = translationSpeeds.vxMetersPerSecond;
                workingSpeeds.vyMetersPerSecond = translationSpeeds.vyMetersPerSecond;
                workingSpeeds.omegaRadiansPerSecond = rotationSpeeds.omegaRadiansPerSecond;

                return workingSpeeds;
            }
        };
    }
}
