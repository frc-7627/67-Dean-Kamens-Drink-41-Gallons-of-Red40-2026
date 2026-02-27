package frc.robot.subsystems.controllable.swivel;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
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

import static frc.robot.Constants.IntakeConstants.*;
import static frc.robot.Constants.CanIDs.*;


final class SwivelImpl extends SubsystemBase implements 
    Swivel, 
    UniControllable<SwivelImpl, SparkMaxControl, SwivelControl>,
    LoggingControllable<SwivelControl>  {

    private static final String LOGGABLE_NAME = "Swivel";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final ControlBox<SwivelControl> controlBox = new ControlBox<>();

    private final SparkMaxWrapper swivelMotor = SparkMaxBuilder.create(
        "Swivel Motor",
        SWIVEL_MOTOR_CAN_ID, 
        MotorType.kBrushless
    ).withConfig(
        new SparkMaxConfig()
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(AMP_LIMIT), 
        ResetMode.kResetSafeParameters, 
        PersistMode.kPersistParameters
    ).build();

    final DoubleSupplier inPositionDegrees = DashboardItems.createDoublePuller(
        "In Position Degrees", 
        // TODO: Constant
        0
    );

    final DoubleSupplier outPositionDegrees = DashboardItems.createDoublePuller(
        "Out Position Degrees", 
        // TODO: Constant
        0
    );

    private final DoubleSupplier motorVelocityRotPerSecSupplier = () -> swivelMotor.queryDouble(
        SparkMaxQuery.ANGULAR_VELOCITY_ROT_PER_SEC
    );

    private final DoubleSupplier motorPositionRotSupplier = () -> swivelMotor.queryDouble(
        SparkMaxQuery.ANGULAR_POSITION_ROT
    );

    private final DoubleSupplier motorAmpsSupplier = () -> swivelMotor.queryDouble(
        SparkMaxQuery.OUTPUT_CURRENT_AMPS
    );

    SwivelImpl() {
        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Velocity RPM")
                ), 
                () -> RPM.convertFrom(
                    motorVelocityRotPerSecSupplier.getAsDouble(), 
                    RotationsPerSecond
                ),
                FunctionalUtil.hasChangedDoublePredicate()
            ),
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Position Degrees")
                ), 
                () -> Degrees.convertFrom(
                    motorPositionRotSupplier.getAsDouble(), 
                    Rotations
                ), 
                FunctionalUtil.hasChangedDoublePredicate()),
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Amps")
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
}
