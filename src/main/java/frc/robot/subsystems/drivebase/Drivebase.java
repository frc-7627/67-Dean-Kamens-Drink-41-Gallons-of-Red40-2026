package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.vision.VisionOdometry;
import com.pathplanner.lib.path.PathConstraints;

public interface Drivebase extends DrivebaseMarker {
    void lock();

    void zeroGyro();

    void resetOdometryToInitial();

    void centerModules();

    void driveWithSpeeds(ChassisSpeeds chassisSpeeds);

    PathConstraints getPathConstraints();

    static Drivebase get(VisionOdometry visionOdometry, Alliance alliance)
            throws DrivebaseConstructorException {
        return new SwerveDrivebase(visionOdometry, alliance);
    }
}
