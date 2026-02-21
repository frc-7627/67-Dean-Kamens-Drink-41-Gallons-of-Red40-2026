package frc.robot.subsystems.controllable.drivebase;

import static frc.robot.Constants.DrivebaseConstants.BLUE_ALLIANCE_INITIAL_POSE;
import static frc.robot.Constants.DrivebaseConstants.MODE;
import static frc.robot.Constants.DrivebaseConstants.RED_ALLIANCE_INITIAL_POSE;
import java.util.Optional;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.control.LoggingControllable;
import frc.robot.setup.teleop.JoystickInputs;
import frc.robot.subsystems.shared.gameinfo.GeneralGameInfoSupplier;
import frc.robot.subsystems.shared.vision.VisionMeasurementsSupplier;

final class SwerveDrivebase extends SubsystemBase implements 
    Drivebase,
    LoggingControllable<DriveControl>
{
    private static final String LOGGABLE_NAME = "Drivebase";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final Optional<VisionMeasurementsSupplier> visionOptional;
    private final SwerveDriveWrapper swerveDriveWrapper;
    private final RotationRateCalculator rotationRateCalculator;
    private final ControlBox<DriveControl> controlBox = new ControlBox<>();

    SwerveDrivebase(
        Optional<VisionMeasurementsSupplier> visionOptional, 
        GeneralGameInfoSupplier gameInfoSupplier
    ) {
        this.visionOptional = visionOptional;

        final Pose2d initialPose = switch (gameInfoSupplier.getAlliance()) {
            case Red -> RED_ALLIANCE_INITIAL_POSE;
            case Blue -> BLUE_ALLIANCE_INITIAL_POSE;
        };

        this.swerveDriveWrapper = new SwerveDriveWrapper(initialPose);

        this.rotationRateCalculator = new RotationRateCalculator(
            KEY_BUILDER.copy(), 
            swerveDriveWrapper::getOrientationRadians
        );

        gameInfoSupplier.onAllianceSet(swerveDriveWrapper::zeroGyroWithAlliance);

        PathPlannerConfig.configure(
            swerveDriveWrapper::getPose, 
            swerveDriveWrapper::resetOdometry, 
            swerveDriveWrapper::getRobotRelativeSpeeds, 
            swerveDriveWrapper::driveRobotRelativeWithFeedForwards, 
            gameInfoSupplier::getAlliance,
            this
        );
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
            vision -> swerveDriveWrapper.updateOdometry(vision.getVisionMeasurements())
        );

        Logger.recordOutput("MyPose2d", swerveDriveWrapper.getPose());
    }

    @Override
    public DriveControl getInputDriveControl(
        JoystickInputs inputs
    ) {
        return new DriveControl() {
            private final Supplier<ChassisSpeeds> inputStream = swerveDriveWrapper
                .getInputStream(
                    MODE, 
                    inputs
                )
            ;


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
                return targetLocation.minus(
                    swerveDriveWrapper.getPose().getTranslation()
                ).getAngle().getRadians();
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
    public void beginControlInner(DriveControl control) {
        control.initialize();
    }

    @Override
    public void runControlInner(DriveControl control) {
        swerveDriveWrapper.driveFieldRelative(
            control.getSpeeds()
        );
    }
}
