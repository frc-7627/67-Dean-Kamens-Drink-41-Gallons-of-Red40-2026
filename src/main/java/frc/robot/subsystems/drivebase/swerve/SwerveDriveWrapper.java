package frc.robot.subsystems.drivebase.swerve;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import static frc.robot.Constants.*;
import static frc.robot.Constants.DrivebaseConstants.*;
import java.io.IOException;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;

public class SwerveDriveWrapper implements Swerve {
    private final SwerveDrive swerveDrive;

    public SwerveDriveWrapper(Alliance alliance) throws SwerveConstructorException {
        final Pose2d initialPose = switch (alliance) {
            case Red -> RED_ALLIANCE_INITIAL_POSE;
            case Blue -> BLUE_ALLIANCE_INITIAL_POSE;
        };

        try {
            this.swerveDrive =
                    new SwerveParser(SWERVE_CONFIG_FILE).createSwerveDrive(MAX_SPEED, initialPose);
        } catch (IOException cause) {
            throw new SwerveConstructorException("Could not create swerve drive!", cause);
        }

    }

    @Override
    public void lock() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lock'");
    }

    @Override
    public void zeroGyro() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'zeroGyro'");
    }

    @Override
    public void resetOdometryToInitial() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetOdometryToInitial'");
    }

    @Override
    public void centerModules() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'centerModules'");
    }

    @Override
    public void driveWithSpeeds(ChassisSpeeds chassisSpeeds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'driveWithSpeeds'");
    }

    @Override
    public PathConstraints getPathConstraints() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPathConstraints'");
    }

    @Override
    public Field2d getField() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getField'");
    }
}
