package frc.robot.subsystems.drivebase;

import java.io.IOException;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.logging.Logger;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.controllers.PathFollowingController;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.util.DriveFeedforwards;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.resources.gameinfo.GeneralGameInfoSupplier;
import frc.robot.resources.pathplanner.PathPlannerConfigurator;
import frc.robot.resources.vision.VisionMeasurementsSupplier;
import swervelib.SwerveDrive;
import swervelib.SwerveInputStream;
import swervelib.parser.SwerveParser;
import static edu.wpi.first.units.Units.Hertz;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static frc.robot.Constants.*;
import static frc.robot.Constants.DrivebaseConstants.*;
import static frc.robot.Constants.OperatorConstants.*;

class SwerveDrivebase extends SubsystemBase implements Drivebase {
    private static final Logger LOGGER = Logger.getLogger(SwerveDrivebase.class.getSimpleName());
    private static final String DASHBOARD_NAME = Drivebase.class.getSimpleName();
    private static final Frequency visionUpdateFrequency = Hertz.of(1);

    private final Timer visionUpdateThrottler = new Timer();
    private final AngularControlSubdashboard angularControlSubdashboard =
            new AngularControlSubdashboard(DASHBOARD_NAME);
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

        swerveDrive.stopOdometryThread();
        visionUpdateThrottler.start();
    }

    private void updateVisionMeasurements() {
        vision.getVisionMeasurements().forEach(visionMeasurement -> {
            swerveDrive.addVisionMeasurement(visionMeasurement.getPose(),
                    visionMeasurement.getTimestamp(), visionMeasurement.getStdDevs());
        });
    }

    @Override
    public void periodic() {
        if (visionUpdateThrottler.hasElapsed(visionUpdateFrequency.asPeriod())) {
            updateVisionMeasurements();
            visionUpdateThrottler.reset();
        }
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

    private ChassisSpeeds getSpeeds() {
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
    public Supplier<ChassisSpeeds> getRotationControl(Rotation2d targetRotation) {
        final Rotation2d targetOrientation = getPose().getRotation().plus(targetRotation);
        return getOrientationControl(() -> targetOrientation);
    }

    @Override
    public Supplier<ChassisSpeeds> getOrientationControl(
            Supplier<Rotation2d> targetOrientationSupplier) {
        return () -> {
            LOGGER.finer("Current angular velocity(per second): " + 
                new Rotation2d(getSpeeds().omegaRadiansPerSecond).toString());
            LOGGER.finer("Current orientation: " + getPose().getRotation().toString());
            LOGGER.finer("Target orientation: " + targetOrientationSupplier.get().toString());
            return new ChassisSpeeds(0, 0,
                angularControlSubdashboard.getController().calculate(
                    getPose().getRotation().getRadians(),
                    targetOrientationSupplier.get().getRadians()
                )
            );
        };
    }

    @Override
    public BooleanSupplier getRotationConvergenceSupplier(Rotation2d targetRotation) {
        final Rotation2d targetOrientation = getPose().getRotation().plus(targetRotation);
        return getOrientationConvergenceSupplier(() -> targetOrientation);
    }

    @Override
    public BooleanSupplier getOrientationConvergenceSupplier(
            Supplier<Rotation2d> targetOrientationSupplier) {
        final Timer timer = new Timer();

        // Check whether we are currently within tolerance.
        final BooleanSupplier checkWithinTolerance = () -> getPose().getRotation().getMeasure()
                .isNear(targetOrientationSupplier.get().getMeasure(), ANGULAR_EPSILON)
                && RadiansPerSecond.of(getSpeeds().omegaRadiansPerSecond)
                        .isNear(RadiansPerSecond.zero(), ANGULAR_VELOCITY_EPSILON);

        // Check that we are currently within tolerance AND have been for the required amount of
        // time.
        return () -> {
            if (checkWithinTolerance.getAsBoolean()) {
                timer.start();

                return timer.hasElapsed(CONVERGENCE_PERIOD);
            } else {
                timer.stop();
                timer.reset();

                return false;
            }
        };
    }
}
