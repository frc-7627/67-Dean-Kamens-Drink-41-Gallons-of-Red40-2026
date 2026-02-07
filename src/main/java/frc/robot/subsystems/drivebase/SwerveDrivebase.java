package frc.robot.subsystems.drivebase;

import java.io.IOException;
import java.util.Optional;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.logging.Logger;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.controllers.PathFollowingController;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.util.DriveFeedforwards;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.gameinfo.GeneralGameInfoSupplier;
import frc.robot.subsystems.pathplanner.PathPlannerConfigurator;
import frc.robot.subsystems.vision.VisionMeasurementsSupplier;
import swervelib.SwerveDrive;
import swervelib.SwerveInputStream;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;
import static frc.robot.Constants.*;
import static frc.robot.Constants.DrivebaseConstants.*;
import static frc.robot.Constants.OperatorConstants.*;

class SwerveDrivebase extends SubsystemBase implements Drivebase {
    private static final Logger LOGGER = Logger.getLogger(SwerveDrivebase.class.getName());
    private static final String DASHBOARD_NAME = Drivebase.class.getName();

    private final Timer visionUpdateThrottler = new Timer();
    private final AngularControl angularControl =
            new AngularControl(DASHBOARD_NAME, this);
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

        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;

        try {
            this.swerveDrive =
                    new SwerveParser(SWERVE_CONFIG_FILE).createSwerveDrive(MAX_SPEED, initialPose);
        } catch (IOException cause) {
            throw new DrivebaseInitException("Could not create swerve drive!", cause);
        }

        swerveDrive.setHeadingCorrection(false);
        swerveDrive.setCosineCompensator(false);
        swerveDrive.setAngularVelocityCompensation(
            true, 
            true, 
            0.1
        );
        swerveDrive.setModuleEncoderAutoSynchronize(
            false, 
            1.0
        );

        swerveDrive.stopOdometryThread();
        visionUpdateThrottler.start();
    }

    private void updateVisionMeasurements() {
        swerveDrive.updateOdometry();
        
        vision.getVisionMeasurements().forEach(visionMeasurement -> {
            swerveDrive.addVisionMeasurement(
                visionMeasurement.getEstimatedPose(),
                visionMeasurement.getTimestamp(), 
                visionMeasurement.getStdDevs()
            );
        });
    }

    @Override
    public void periodic() {
        updateVisionMeasurements();
    }

    /**
     * 
     * @param x
     * @param y
     * @param rot
     * @return
     */
    private SwerveInputStream getDefaultInput(DoubleSupplier x, DoubleSupplier y,
            DoubleSupplier rot) {
        return SwerveInputStream.of(swerveDrive, x, y).withControllerRotationAxis(rot)
                .deadband(DEADBAND).scaleTranslation(0.8).allianceRelativeControl(true);
    }

    private void resetOdometry(Pose2d pose) {
        swerveDrive.resetOdometry(pose);
    }

    @Override
    public ChassisSpeeds getSpeeds() {
        return swerveDrive.getRobotVelocity();
    }

    private void driveWithSpeedsAndFeedForwards(ChassisSpeeds speeds,
            DriveFeedforwards feedforwards) {
        swerveDrive.drive(speeds, swerveDrive.kinematics.toSwerveModuleStates(speeds),
                feedforwards.linearForces());
    }

    private PathFollowingController getController() {
        return new PPHolonomicDriveController(
                // Translation PID Constants
                new PIDConstants(5.0, 0.0, 0.0),
                // Rotation PID Constants
                new PIDConstants(5.0, 0.0, 0.0));
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
        swerveDrive.driveFieldOriented(chassisSpeeds);
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
        final boolean gotConfigurator = this.gotConfigurator;
        this.gotConfigurator = true;
        return gotConfigurator ? Optional.empty()
                : Optional.of(PathPlannerConfigurator.create(this::getPose, this::resetOdometry,
                        this::getSpeeds, this::driveWithSpeedsAndFeedForwards, getController(),
                        gameInfoSupplier, this));
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

    @Override
    public AngularControl getAngularControl() {
        return angularControl;
    }
}
