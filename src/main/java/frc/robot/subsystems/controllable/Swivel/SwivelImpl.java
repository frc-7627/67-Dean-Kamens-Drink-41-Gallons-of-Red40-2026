package frc.robot.subsystems.controllable.Swivel;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.MAXMotionConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.MAXMotionConfig.MAXMotionPositionMode;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.List;
import java.util.function.DoubleSupplier;

import frc.bofalib.control.Controllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.control.LoggingControllable;
import frc.bofalib.generic.control.UniControllable;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxBuilder;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxWrapper;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.query.SparkMaxQuery;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.bofalib.util.FunctionalUtil;
import static frc.robot.Constants.SwivelConstants.*;
import static frc.robot.Constants.CanIDs.*;


final class SwivelImpl extends SubsystemBase implements 
    Swivel, 
    UniControllable<SwivelImpl, SparkMaxControl, SwivelControl>,
    LoggingControllable<SwivelControl>  {

    private static final String LOGGABLE_NAME = "Swivel";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final ControlBox<SwivelControl> controlBox = new ControlBox<>();

    final SparkMaxWrapper swivelMotor = SparkMaxBuilder.create(
        "Swivel Motor",
        SWIVEL_MOTOR_CAN_ID, 
        MotorType.kBrushless
    ).withConfig(
        new SparkMaxConfig()
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(AMP_LIMIT)
            .apply(new ClosedLoopConfig().apply(
                new MAXMotionConfig()
                    // TODO: constants
                    .allowedProfileError(Rotations.convertFrom(0.5, Degrees))
                    .cruiseVelocity(50 * SWIVEL_TO_MOTOR_GEAR_RATIO /* RPM */)
                    .maxAcceleration(25 * SWIVEL_TO_MOTOR_GEAR_RATIO /* RPM / s */)
                    .positionMode(MAXMotionPositionMode.kMAXMotionTrapezoidal)
                ).p(1.0)
            ), 
        ResetMode.kResetSafeParameters, 
        PersistMode.kPersistParameters
    ).build();

    final DoubleSupplier inPositionDegrees = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("In Position Degrees"), 
        true,
        IN_POSITION_DEGREES
    );

    final DoubleSupplier outPositionDegrees = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Out Position Degrees"), 
        true,
        OUT_POSITION_DEGREES
    );

    private final DoubleSupplier swivelVelocityRotPerSecSupplier = () -> swivelMotor.queryDouble(
        SparkMaxQuery.ANGULAR_VELOCITY_ROT_PER_SEC
    ) / SWIVEL_TO_MOTOR_GEAR_RATIO;

    private final DoubleSupplier swivelPositionRotSupplier = () -> swivelMotor.queryDouble(
        SparkMaxQuery.ANGULAR_POSITION_ROT 
    ) / SWIVEL_TO_MOTOR_GEAR_RATIO;

    private final DoubleSupplier motorAmpsSupplier = () -> swivelMotor.queryDouble(
        SparkMaxQuery.OUTPUT_CURRENT_AMPS
    );

    SwivelImpl() {
        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Swivel Velocity RPM"),
                    true
                ), 
                () -> RPM.convertFrom(
                    swivelVelocityRotPerSecSupplier.getAsDouble(), 
                    RotationsPerSecond
                ),
                FunctionalUtil.hasChangedDoublePredicate()
            ),
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Swivel Position Degrees"),
                    true
                ), 
                () -> Degrees.convertFrom(
                    swivelPositionRotSupplier.getAsDouble(), 
                    Rotations
                ), 
                FunctionalUtil.hasChangedDoublePredicate()),
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Amps"),
                    true
                ), 
                () -> motorAmpsSupplier.getAsDouble(),
                FunctionalUtil.hasChangedDoublePredicate()
            )
        ));
    }

    @Override
    public String getLoggableName() {
        return LOGGABLE_NAME;
    }

    @Override
    public ControlBox<SwivelControl> getControlBox() {
        return controlBox;
    }

    @Override
    public SwivelImpl getThis() {
        return this;
    }

    @Override
    public Controllable<SparkMaxControl> getFirstControllable() {
        return swivelMotor;
    }

    @Override
    public boolean queryBoolean(SwivelBooleanQuery query) {
        return switch (query) {
            case AT_UP_POSE -> 
                Degrees.convertFrom(swivelMotor.queryDouble(SparkMaxQuery.ANGULAR_POSITION_ROT), Rotations)
            >= inPositionDegrees.getAsDouble();
            case AT_DOWN_POSE ->
                Degrees.convertFrom(swivelMotor.queryDouble(SparkMaxQuery.ANGULAR_POSITION_ROT), Rotations)
            <= outPositionDegrees.getAsDouble();
        };
    }

    @Override
    public void reset() {
        swivelMotor.zeroEncoder();
    }
}
