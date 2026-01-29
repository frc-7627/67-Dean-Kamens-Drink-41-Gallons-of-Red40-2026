package frc.robot.subsystems.drivebase;

import java.io.IOException;
import java.util.Optional;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.resources.gameinfo.GeneralGameInfoSupplier;
import frc.robot.resources.pathplanner.PathPlannerConfigurator;
import frc.robot.resources.vision.VisionMeasurementsSupplier;
import swervelib.SwerveDrive;
import swervelib.SwerveInputStream;
import swervelib.parser.SwerveParser;
import static frc.robot.Constants.*;
import static frc.robot.Constants.DrivebaseConstants.*;
import static frc.robot.Constants.OperatorConstants.*;

class SwerveDrivebase extends SubsystemBase implements Drivebase {
    private final GeneralGameInfoSupplier gameInfoSupplier;
    private final VisionMeasurementsSupplier vision;
    private final SwerveDrive swerveDrive;

    SwerveDrivebase(VisionMeasurementsSupplier vision, GeneralGameInfoSupplier gameInfoSupplier)
            throws DrivebaseInitException {
        this.gameInfoSupplier = gameInfoSupplier;
        this.vision = vision;

        final Pose2d initialPose = switch (gameInfoSupplier.getAlliance()) {
            case Red -> RED_ALLIANCE_INITIAL_POSE;
            case Blue -> BLUE_ALLIANCE_INITIAL_POSE;
        };

        try {
            this.swerveDrive =
                    new SwerveParser(SWERVE_CONFIG_FILE).createSwerveDrive(MAX_SPEED, initialPose);
        } catch (IOException cause) {
            throw new DrivebaseInitException("Could not create swerve drive!", cause);
        }

    }

    private SwerveInputStream getDefaultInput(DoubleSupplier x, DoubleSupplier y,
            DoubleSupplier rot) {
        return SwerveInputStream
                .of(swerveDrive, x, y)
                .withControllerRotationAxis(rot)
                .deadband(DEADBAND).scaleTranslation(0.8)
                .allianceRelativeControl(true);
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
    public Pose2d getPose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPose'");
    }

    @Override
    public Optional<PathPlannerConfigurator> getPathPlannerConfigurator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPathPlannerConfigurator'");
    }

    @Override
    public Supplier<ChassisSpeeds> getInput(DoubleSupplier x, DoubleSupplier y,
            DoubleSupplier rot) {
        return getDefaultInput(x, y, rot);
    }

    @Override
    public void setBrake(boolean brake) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBrake'");
    }
}
