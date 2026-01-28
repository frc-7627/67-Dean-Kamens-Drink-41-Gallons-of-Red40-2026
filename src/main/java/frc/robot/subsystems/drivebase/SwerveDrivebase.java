package frc.robot.subsystems.drivebase;

import java.io.IOException;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.vision.VisionOdometry;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import static frc.robot.Constants.*;
import static frc.robot.Constants.DrivebaseConstants.*;

class SwerveDrivebase extends SubsystemBase implements Drivebase {
    private final VisionOdometry vision;
    private final SwerveDrive swerveDrive;

    SwerveDrivebase(VisionOdometry vision, Alliance alliance)
            throws DrivebaseConstructorException {
        this.vision = vision;

        final Pose2d initialPose = switch (alliance) {
            case Red -> RED_ALLIANCE_INITIAL_POSE;
            case Blue -> BLUE_ALLIANCE_INITIAL_POSE;
        };

        try {
            this.swerveDrive =
                    new SwerveParser(SWERVE_CONFIG_FILE).createSwerveDrive(MAX_SPEED, initialPose);
        } catch (IOException cause) {
            throw new DrivebaseConstructorException("Could not create swerve drive!", cause);
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
}
