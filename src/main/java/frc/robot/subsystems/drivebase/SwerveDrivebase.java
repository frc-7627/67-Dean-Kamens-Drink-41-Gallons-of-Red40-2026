package frc.robot.subsystems.drivebase;

import java.io.IOException;
import java.util.Optional;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
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
    private boolean gotConfigurator;

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

    private void updateVisionMeasurements() {
        vision.getVisionMeasurements().forEach(visionMeasurement -> {
            swerveDrive.addVisionMeasurement(visionMeasurement.getPose(),
                    visionMeasurement.getTimestamp(), visionMeasurement.getStdDevs());
        });
    }

    @Override
    public void periodic() {
        updateVisionMeasurements();
    }

    private SwerveInputStream getDefaultInput(DoubleSupplier x, DoubleSupplier y,
            DoubleSupplier rot) {
        return SwerveInputStream.of(swerveDrive, x, y).withControllerRotationAxis(rot)
                .deadband(DEADBAND).scaleTranslation(0.8).allianceRelativeControl(true);
    }

    @Override
    public void lock() {
        swerveDrive.lockPose();
    }

    @Override
    public void zeroGyro() {
        swerveDrive.zeroGyro();
    }

    @Override
    public void driveWithSpeeds(ChassisSpeeds chassisSpeeds) {
        swerveDrive.drive(chassisSpeeds);
    }

    @Override
    public PathConstraints getPathConstraints() {
        return new PathConstraints(swerveDrive.getMaximumChassisVelocity(), 4.0,
                swerveDrive.getMaximumChassisAngularVelocity(), Units.degreesToRadians(720));
    }

    @Override
    public Pose2d getPose() {
        return swerveDrive.getPose();
    }

    @Override
    public Optional<PathPlannerConfigurator> getPathPlannerConfigurator() {
        // TODO Auto-generated method stub
        final boolean gotConfigurator = this.gotConfigurator;
        this.gotConfigurator = true;
        throw new UnsupportedOperationException(
                "Unimplemented method 'getPathPlannerConfigurator'");
    }

    @Override
    public Supplier<ChassisSpeeds> getInput(DoubleSupplier x, DoubleSupplier y,
            DoubleSupplier rot) {
        return getDefaultInput(x, y, rot);
    }

    @Override
    public void setBrake(boolean brake) {
        swerveDrive.setMotorIdleMode(brake);
    }
}
