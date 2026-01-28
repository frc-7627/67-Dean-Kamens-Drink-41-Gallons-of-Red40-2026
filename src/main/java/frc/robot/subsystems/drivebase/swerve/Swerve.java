package frc.robot.subsystems.drivebase.swerve;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;

public interface Swerve {
    void lock();

    void zeroGyro();

    void resetOdometryToInitial();

    void centerModules();

    void driveWithSpeeds(ChassisSpeeds chassisSpeeds);

    PathConstraints getPathConstraints();

    Field2d getField();
}
