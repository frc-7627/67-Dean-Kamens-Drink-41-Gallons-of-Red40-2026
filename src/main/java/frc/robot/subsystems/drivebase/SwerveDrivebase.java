package frc.robot.subsystems.drivebase;

import static frc.robot.Constants.DrivebaseConstants.BLUE_ALLIANCE_INITIAL_POSE;
import static frc.robot.Constants.DrivebaseConstants.RED_ALLIANCE_INITIAL_POSE;
import java.util.Objects;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.dashboard.KeyBuilder;
import frc.robot.subsystems.vision.VisionMeasurementsSupplier;

final class SwerveDrivebase extends SubsystemBase implements Drivebase {
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of("Drivebase");
    private static final DriveControl ZERO_DRIVE_CONTROL = new DriveControl() {
        private static final ChassisSpeeds ZERO_SPEEDS = new ChassisSpeeds();

        @Override
        public ChassisSpeeds getSpeeds() {
            return ZERO_SPEEDS;
        }
    };

    private final VisionMeasurementsSupplier vision;
    private final SwerveDriveWrapper swerveDriveWrapper;
    private final RotationRateCalculator rotationRateCalculator;
    private DriveControl driveControl = ZERO_DRIVE_CONTROL;

    SwerveDrivebase(VisionMeasurementsSupplier vision, Supplier<Alliance> allianceSupplier) {
        this.vision = vision;

        final Pose2d initialPose = switch (allianceSupplier.get()) {
            case Red -> RED_ALLIANCE_INITIAL_POSE;
            case Blue -> BLUE_ALLIANCE_INITIAL_POSE;
        };

        this.swerveDriveWrapper = new SwerveDriveWrapper(initialPose);

        this.rotationRateCalculator = new RotationRateCalculator(
            KEY_BUILDER.copy(), 
            swerveDriveWrapper::getOrientationRadians
        );

        PathPlannerConfig.configure(
            swerveDriveWrapper::getPose, 
            swerveDriveWrapper::resetOdometry, 
            swerveDriveWrapper::getRobotRelativeSpeeds, 
            swerveDriveWrapper::driveRobotRelativeWithFeedForwards, 
            allianceSupplier,
            this
        );
    }

    @Override
    public void periodic() {
        swerveDriveWrapper.updateOdometry(vision.getVisionMeasurements());
    }

    @Override
    public DriveControl getInputDriveControl(DoubleSupplier xInput, DoubleSupplier yInput,
            DoubleSupplier rotInput) {
        return new DriveControl() {
            private final Supplier<ChassisSpeeds> inputStream = swerveDriveWrapper.getInputStream(
                xInput, 
                yInput, 
                rotInput
            );

            @Override
            public ChassisSpeeds getSpeeds() {
                return inputStream.get();
            }
        };
    }

    @Override
    public DriveControl getAngularDriveControl(AngleTargetter angleTargetter) {
        return new DriveControl() {
            private final ChassisSpeeds workingSpeeds = new ChassisSpeeds();

            @Override
            public void initialize() {
                rotationRateCalculator.reset();
                angleTargetter.initialize();
            }

            @Override
            public ChassisSpeeds getSpeeds() {
                workingSpeeds.omegaRadiansPerSecond = 
                    rotationRateCalculator.calculateRadiansPerSecond(
                        angleTargetter.getTargetRadians()
                    )
                ;

                return workingSpeeds;
            }
        };
    }

    @Override
    public DriveControl getZeroDriveControl() {
        return ZERO_DRIVE_CONTROL;
    }

    @Override
    public AngleTargetter getRotationAngleTargetter(Rotation2d targetRotation) {
        final double targetRotationRadians = targetRotation.getRadians();

        return new AngleTargetter() {
            private double initialOrientationRadians;

            @Override
            public void initialize() {
                initialOrientationRadians = swerveDriveWrapper.getOrientationRadians();
            }

            @Override
            public double getTargetRadians() {
                return initialOrientationRadians + targetRotationRadians;
            }
        };
    }

    @Override
    public AngleTargetter getLocationAngleTargetter(
            Supplier<Translation2d> targetLocationSupplier) {
        return new AngleTargetter() {
            @Override
            public double getTargetRadians() {
                return targetLocationSupplier.get().minus(
                    swerveDriveWrapper.getPose().getTranslation()
                ).getAngle().getRadians();
            }
        };
    }

    @Override
    public void setDriveControl(DriveControl driveControl) {
        this.driveControl = Objects.requireNonNull(driveControl);
    }

    @Override
    public void lock() {
        swerveDriveWrapper.lock();
    }

    @Override
    public void zeroGyro() {
        swerveDriveWrapper.zeroGyro();
    }

    @Override
    public PathConstraints getPathConstraints() {
        return new PathConstraints(
            swerveDriveWrapper.getMaxVelocityMetersPerSecond(), 
            4.0,
            swerveDriveWrapper.getMaxAngularVelocityRadPerSecond(), 
            Units.degreesToRadians(720)
        );
    }

    @Override
    public void setBrake(boolean brake) {
        swerveDriveWrapper.setBrake(brake);;
    }

    @Override
    public void drive() {
        swerveDriveWrapper.driveFieldRelative(
            driveControl.getSpeeds()
        );
    }
}
