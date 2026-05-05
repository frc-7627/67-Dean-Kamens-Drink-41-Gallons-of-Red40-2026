package frc.robot.subsystems.controllable.drivebase;

import static frc.robot.Constants.DrivebaseConstants.RED_ALLIANCE_INITIAL_POSE;
import static frc.robot.Constants.DrivebaseConstants.BLUE_ALLIANCE_INITIAL_POSE;
import static frc.robot.Constants.DrivebaseConstants.MODE;
import static frc.robot.Constants.VisionConstants.VISION_ENABLED;

import java.util.List;
import java.util.Optional;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.control.LoggingControllable;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.bofalib.util.FunctionalUtil;
import frc.robot.Constants;
import frc.robot.setup.teleop.JoystickInputs;
import frc.robot.subsystems.shared.gameinfo.GameInfoSupplier;
import frc.robot.subsystems.shared.gameinfo.GeneralGameInfoSupplier;
import frc.robot.subsystems.shared.vision.VisionMeasurementsSupplier;
import swervelib.SwerveDrive;


final class SwerveDrivebase extends SubsystemBase implements
        Drivebase,
        LoggingControllable<DriveControl> {
    private static final String LOGGABLE_NAME = "Drivebase";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final Optional<VisionMeasurementsSupplier> visionOptional;
    private final SwerveDriveWrapper swerveDriveWrapper;
    private final RotationRateCalculator rotationRateCalculator;
    private final ControlBox<DriveControl> controlBox = new ControlBox<>();
    private final GameInfoSupplier gameInfoSupplier;

    SwerveDrivebase(
            Optional<VisionMeasurementsSupplier> visionOptional,
            GameInfoSupplier gameInfoSupplier) {
        this.visionOptional = visionOptional;
        this.gameInfoSupplier = gameInfoSupplier;

        final Pose2d initialPose = switch (gameInfoSupplier.getAlliance()) {
            case Red -> RED_ALLIANCE_INITIAL_POSE;
            case Blue -> BLUE_ALLIANCE_INITIAL_POSE;
        };

        this.swerveDriveWrapper = new SwerveDriveWrapper(initialPose);

        this.rotationRateCalculator = new RotationRateCalculator(
                KEY_BUILDER.copy(),
                swerveDriveWrapper::getOrientationRadians);

        PathPlannerConfig.configure(
                swerveDriveWrapper::getPose,
                swerveDriveWrapper::resetOdometry,
                swerveDriveWrapper::getRobotRelativeSpeeds,
                swerveDriveWrapper::driveRobotRelativeWithFeedForwards,
                gameInfoSupplier::getAlliance,
                this);

        if (!VISION_ENABLED) {
            zeroGyroWithAlliance();
        }

        CommandSchedulerWrapper.getInstance().registerPeriodicAction(
                FunctionalUtil.composeConditional(
                        DashboardItems.createDoublePusher(
                                KEY_BUILDER.copyExtendedToString("Feet to Hub"),
                                true
                            ),
                        () -> Units.metersToFeet(swerveDriveWrapper.getTargetMeters(gameInfoSupplier.getAlliance())),
                        FunctionalUtil.hasChangedDoublePredicate()));

    }

    @Override
    public ControlBox<DriveControl> getControlBox() {
        return controlBox;
    }

    @Override
    public String getLoggableName() {
        return LOGGABLE_NAME;
    }

    @Override
    public void periodic() {
        visionOptional.ifPresent(
                vision -> swerveDriveWrapper.updateOdometry(vision.getVisionMeasurements()));

        Logger.recordOutput("MyPose2d", swerveDriveWrapper.getPose());
    }

    @Override
    public DriveControl getInputDriveControl(
            JoystickInputs inputs) {
        return new DriveControl() {
            private final Supplier<ChassisSpeeds> inputStream = swerveDriveWrapper
                    .getInputStream(
                            MODE,
                            inputs);

            @Override
            public String getLoggableName() {
                return "Input Drive Control";
            }

            @Override
            public String getLoggableInfo() {
                // TODO Auto-generated method stub
                return DriveControl.super.getLoggableInfo();
            }

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
            public String getLoggableName() {
                return "Angular Drive Control";
            }

            @Override
            public String getLoggableInfo() {
                // TODO Auto-generated method stub
                return DriveControl.super.getLoggableInfo();
            }

            @Override
            public void initialize() {
                rotationRateCalculator.reset();
                angleTargetter.initialize();
            }

            @Override
            public ChassisSpeeds getSpeeds() {
                workingSpeeds.omegaRadiansPerSecond = rotationRateCalculator.calculateRadiansPerSecond(
                        angleTargetter.getTargetRadians());

                return workingSpeeds;
            }
        };
    }

    @Override
    public AngleTargetter getRotationAngleTargetter(Rotation2d targetRotation) {
        final double targetRotationRadians = targetRotation.getRadians();

        return new AngleTargetter() {
            private double initialOrientationRadians;

            @Override
            public String getLoggableName() {
                return "Rotation Angle Targetter";
            }

            @Override
            public String getLoggableInfo() {
                // TODO Auto-generated method stub
                return AngleTargetter.super.getLoggableInfo();
            }

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
    public AngleTargetter getLocationAngleTargetter(Translation2d targetLocation) {
        return getLocationSupplierAngleTargetter(() -> targetLocation);
    }

    @Override
    public AngleTargetter getLocationSupplierAngleTargetter(Supplier<Translation2d> targetLocationSupplier) {
        return new AngleTargetter() {
            @Override
            public String getLoggableName() {
                return "Location Angle Targetter";
            }

            @Override
            public String getLoggableInfo() {
                // TODO Auto-generated method stub
                return AngleTargetter.super.getLoggableInfo();
            }

            @Override
            public double getTargetRadians() {
                return targetLocationSupplier
                    .get()
                    .minus(
                        swerveDriveWrapper.getPose().getTranslation()
                    ).getAngle()
                    .getRadians()
                ;
            }
        };
    }

    @Override
    public DistanceTargetter getDistanceTargetterToHub() {
        return new DistanceTargetter() {
            @Override
            public String getLoggableName() {
                return "Location Distance Targetter to Hub";
            }

            @Override
            public String getLoggableInfo() {
                // TODO Auto-generated method stub
                return DistanceTargetter.super.getLoggableInfo();
            }

            @Override
            public double getTargetMeters() {
                if (gameInfoSupplier.getAlliance() == Alliance.Red) {
                    return swerveDriveWrapper.getPose().getTranslation()
                            .getDistance(Constants.VisionConstants.RED_HUB_LOCATION); //TODO: Change this to reef loco
                } else {
                    return swerveDriveWrapper.getPose().getTranslation()
                            .getDistance(Constants.VisionConstants.BLUE_HUB_LOCATION);
                }
            }
        };
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
    public void zeroGyroWithAlliance() {
        Alliance alliance = this.gameInfoSupplier.getAlliance();
        swerveDriveWrapper.zeroGyroWithAlliance(alliance);
    }

    @Override
    public PathConstraints getPathConstraints() {
        return new PathConstraints(
                swerveDriveWrapper.getMaxVelocityMetersPerSecond(),
                4.0,
                swerveDriveWrapper.getMaxAngularVelocityRadPerSecond(),
                Units.degreesToRadians(720));
    }

    @Override
    public void setBrake(boolean brake) {
        swerveDriveWrapper.setBrake(brake);
        ;
    }

    @Override
    public void beginControlInner(DriveControl control) {
        control.initialize();
    }

    @Override
    public void runControlInner(DriveControl control) {
        swerveDriveWrapper.driveFieldRelative(
                control.getSpeeds());
    }

    @Override
    public ChassisSpeeds getFieldRelativeSpeeds() {
        return swerveDriveWrapper.getFieldRelativeSpeeds();
    }

    @Override
    public ChassisSpeeds getRobotRelativeSpeeds() {
        return swerveDriveWrapper.getRobotRelativeSpeeds();
    }

    @Override
    public Translation2d getAccelerations() {
        return swerveDriveWrapper.getAccelerations();
    }
}
