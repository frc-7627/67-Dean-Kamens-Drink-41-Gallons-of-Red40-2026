package frc.robot.subsystems.drivebase;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;
import com.pathplanner.lib.path.PathConstraints;

public interface Drivebase extends Subsystem {
    void lock();

    void zeroGyro();

    void resetOdometryToInitial();

    void centerModules();

    void driveWithSpeeds(ChassisSpeeds chassisSpeeds);

    PathConstraints getPathConstraints();
}
