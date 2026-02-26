package frc.robot.subsystems.controllable.drivebase;

import static frc.robot.Constants.MAX_SPEED;
import static frc.robot.Constants.DrivebaseConstants.*;
import static frc.robot.Constants.OperatorConstants.DEADBAND;
import static frc.robot.Constants.VisionConstants.VISION_ENABLED;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.Stream;
import com.pathplanner.lib.util.DriveFeedforwards;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.bofalib.util.FunctionalUtil;
import frc.robot.setup.teleop.JoystickInputs;
import frc.robot.subsystems.shared.vision.VisionMeasurement;
import swervelib.SwerveDrive;
import swervelib.SwerveInputStream;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

final class SwerveDriveWrapper {

    private static final Rotation3d k180deg = new Rotation3d();

    private static final Logger logger = Logger.getLogger(SwerveDriveWrapper.class.getName());

    private final SwerveDrive swerveDrive;

    /**
     * Configure the swerve drive.
     * 
     * @param swerveDrive the swerve drive
     */
    private static void configureSwerveDrive(SwerveDrive swerveDrive) {
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

        if (VISION_ENABLED) {
            swerveDrive.stopOdometryThread();
        }
    }

    /**
     * Get a configured swerve drive at the initial pose.
     * 
     * @param initialPose the initial pose
     * @return a configured swerve drive
     */
    private static SwerveDrive getConfiguredSwerveDrive(Pose2d initialPose) {
        final SwerveDrive swerveDrive;

        try {
            swerveDrive = new SwerveParser(SWERVE_CONFIG_FILE)
                .createSwerveDrive(MAX_SPEED, initialPose);
        } catch (IOException cause) {
            throw new DrivebaseInitError("Could not create swerve drive!", cause);
        }

        configureSwerveDrive(swerveDrive);
        
        return swerveDrive;
    }

    /**
     * A wrapper around a swerve drive, starting at the initial pose.
     * 
     * @param initialPose the initial pose
     */
    SwerveDriveWrapper(Pose2d initialPose) {
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;

        this.swerveDrive = getConfiguredSwerveDrive(initialPose);
    }

    /**
     * Update odometry with the vision measurements.
     * 
     * @param visionMeasurements the vision measurements
     */
    void updateOdometry(Stream<VisionMeasurement> visionMeasurements) {
        visionMeasurements.forEach(visionMeasurement -> {
            swerveDrive.addVisionMeasurement(
                visionMeasurement.getEstimatedPose(),
                visionMeasurement.getTimestamp(), 
                visionMeasurement.getStdDevs()
            );
        });

        swerveDrive.updateOdometry();
    }

    /**
     * Sets the motor mode to brake
     * 
     * @param brake is the brake on or off
     */
    void setBrake(boolean brake) {
        swerveDrive.setMotorIdleMode(brake);
    }

    /**
     * Reset odometry to the pose.
     * 
     * @param pose the pose
     */
    void resetOdometry(Pose2d pose) {
        swerveDrive.resetOdometry(pose);
    }

    /**
     * @return the current pose
     */
    Pose2d getPose() {
        return swerveDrive.getPose();
    }

    /**
     * @return the current speeds, relative to the robot
     */
    ChassisSpeeds getRobotRelativeSpeeds() {
        return swerveDrive.getRobotVelocity();
    }

    /**
     * Lock the robot's pose.
     */
    void lock() {
        swerveDrive.lockPose();
    }

    /**
     * Reset the gyro to zero.
     */
    void zeroGyro() {
        swerveDrive.zeroGyro();
        System.out.println("Zeroing the gyro");
        logger.fine("Zeroing the gyro");
        //swerveDrive.setGyroOffset(swerveDrive.getGyro().getRotation3d().minus(k180deg));
    }

    void zeroGyroWithAlliance(Alliance alliance) {
        zeroGyro();
        System.out.println("Zeroing Gyro with the alliance"); 
        logger.fine("Zeroing Gyro with the alliance");

        if (alliance.equals(Alliance.Red)) {
            resetOdometry(new Pose2d(getPose().getTranslation(), Rotation2d.k180deg));
            System.out.println("Team was found to be red so applying the 180 degree thing");
            logger.fine("Team was found to be red so applying the 180 degree thing");
        }
    }

    /**
     * Drive with the field relative speeds.
     * 
     * @param fieldRelativeSpeeds the field relative speeds
     */
    void driveFieldRelative(ChassisSpeeds fieldRelativeSpeeds) {
        swerveDrive.driveFieldOriented(fieldRelativeSpeeds);
    }

    /**
     * Drive with the robot relative speeds and the feedforwards.
     * @param robotRelativeSpeeds the robot relative speeds.
     * @param feedforwards
     */
    void driveRobotRelativeWithFeedForwards(
        ChassisSpeeds robotRelativeSpeeds,
        DriveFeedforwards feedforwards
    ) {
        swerveDrive.drive(
            robotRelativeSpeeds, 
            swerveDrive.kinematics.toSwerveModuleStates(robotRelativeSpeeds),
            feedforwards.linearForces()
        );
    }

    double getMaxVelocityMetersPerSecond() {
        return swerveDrive.getMaximumChassisVelocity();
    }

    double getMaxAngularVelocityRadPerSecond() {
        return swerveDrive.getMaximumChassisAngularVelocity();
    }

    double getOrientationRadians() {
        return swerveDrive.getOdometryHeading().getRadians();
    }

    private SwerveInputStream getTranslationalInputStream(
        JoystickInputs inputs
    ) {
        return SwerveInputStream.of(
            swerveDrive, 
            () -> -inputs.leftY().getAsDouble(), 
            () -> -inputs.leftX().getAsDouble()
        ).deadband(DEADBAND)
        .scaleTranslation(0.8)
        .allianceRelativeControl(true);
    }

    SwerveInputStream getInputStream(
        InputMode mode,
        JoystickInputs inputs
    ) {
        return switch (mode) {
            case ROTATE -> getTranslationalInputStream(inputs)
                .withControllerRotationAxis(FunctionalUtil.negativeSupplier(inputs.rightX()));
            case HEADING -> getTranslationalInputStream(inputs)
                .withControllerHeadingAxis(inputs.rightX(), inputs.rightY())
                .headingWhile(true);
        };
    }
}
