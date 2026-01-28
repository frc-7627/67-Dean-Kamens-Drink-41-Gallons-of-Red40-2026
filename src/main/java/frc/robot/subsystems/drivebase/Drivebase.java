package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import com.pathplanner.lib.path.PathConstraints;

public interface Drivebase extends DrivebaseMarker {
    void lock();

    void zeroGyro();

    void resetOdometryToInitial();

    void centerModules();

    void driveWithSpeeds(ChassisSpeeds chassisSpeeds);

    PathConstraints getPathConstraints();
}
