package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.vision.VisionMeasurementsSupplier;

public interface Drivebase extends PathConstraintsSupplier {
    void lock();

    void zeroGyro();

    void resetOdometryToInitial();

    void centerModules();

    void driveWithSpeeds(ChassisSpeeds chassisSpeeds);

    static Drivebase get(VisionMeasurementsSupplier vision, Alliance alliance)
            throws DrivebaseConstructorException {
        return new SwerveDrivebase(vision, alliance);
    }
}
