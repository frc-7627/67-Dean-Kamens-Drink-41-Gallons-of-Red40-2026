package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;

public interface DriveControl
{
    default void initialize() {}

    ChassisSpeeds getSpeeds();

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
