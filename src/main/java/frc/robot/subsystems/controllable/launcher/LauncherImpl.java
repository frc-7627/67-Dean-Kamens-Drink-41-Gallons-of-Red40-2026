package frc.robot.subsystems.controllable.launcher;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Feet;
import static edu.wpi.first.units.Units.FeetPerSecond;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static frc.robot.Constants.USE_SHOOT_SPEED_COMPENSATION;
import static frc.robot.Constants.CanIDs.LAUNCHER_COMMANDER_CAN_ID;
import static frc.robot.Constants.CanIDs.LAUNCHER_MINION_CAN_ID;
import static frc.robot.Constants.LauncherConstants.*;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.DoubleSupplier;
import java.util.logging.Logger;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.control.Controllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.gains.GainItem;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.control.LoggingControllable;
import frc.bofalib.generic.control.UniControllable;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXBuilder;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXGroup;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXGroupBuilder;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talonfx.gains.TalonFXSettingGains;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXGroupQuery;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXQuery;
import frc.bofalib.generic.music.UniInstrument;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.bofalib.util.FunctionalUtil;
import frc.robot.subsystems.controllable.drivebase.DistanceTargetter;
import frc.robot.subsystems.controllable.drivebase.DrivebaseKinematics;

// Colloquially known as Miles after bad Chinese
final class LauncherImpl extends SubsystemBase implements 
    Launcher,
    UniControllable<LauncherImpl, TalonFXBatchControl, LauncherControl>,
    UniInstrument<TalonFXGroup>,
    LoggingControllable<LauncherControl>
{
    private static final String LOGGABLE_NAME = "Launcher";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);
    private static final Logger LOGGER = Logger.getLogger(LauncherImpl.class.getName());

    private final ControlBox<LauncherControl> controlBox = new ControlBox<>();

    final TalonFXGroup motors = TalonFXGroupBuilder.create(
        "Launcher Motors", 
        TalonFXBuilder.create("Launcher - Commander", LAUNCHER_COMMANDER_CAN_ID)
    ).withFollower(
        TalonFXBuilder.create("Launcher - Minion", LAUNCHER_MINION_CAN_ID), 
        MotorAlignmentValue.Aligned
    ).withAllConfig(
        new TalonFXConfiguration()
            .withMotorOutput(MOTOR_OUTPUT_CONFIGS)
            .withAudio(AUDIO_CONFIGS)
    ).build();

    private final DoubleSupplier motorVelocityRotPerSecSupplier = () -> motors.queryDouble(
        new TalonFXGroupQuery(
            OptionalInt.empty(), 
            TalonFXQuery.ANGULAR_VELOCITY_ROT_PER_SEC
        )
    );
    private final DoubleSupplier motorVoltageSupplier = () -> motors.queryDouble(
        new TalonFXGroupQuery(
            OptionalInt.empty(),
            TalonFXQuery.VOLTAGE
        )
    );

    final DoubleSupplier shootSpeedFPSSupplier =
    DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Shoot Feet Per Sec"), 
        true,
        DEFAULT_SHOOT_FPS
    );

    final DoubleSupplier activeIdleFPSSupplier =
    DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Active Idle Feet Per Sec"),
        true,
        DEFAULT_ACTIVE_IDLE_FPS
    );

    final DoubleSupplier inactiveIdleFPSSupplier =
    DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Inactive Idle Feet Per Sec"), 
        true,
        DEFAULT_INACTIVE_IDLE_FPS
    );

    final DoubleSupplier manualCompensationFPSSupplier =
    DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Manual Compensation Feet Per Sec"), 
        true,
        0
    );

    final DrivebaseKinematics kinematics;

    private Optional<DoubleSupplier> targetRPSSupplier = Optional.empty();

    LauncherImpl(DrivebaseKinematics kinematics) {
        this.kinematics = kinematics;

        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            /*FunctionalUtil.composeConditional(
                motors.getConfigurator()::applyCurrentLimit, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Current Limit"), 
                    true,
                    DEFAULT_CURRENT_LIMIT
                ), 
                FunctionalUtil.hasChangedDoublePredicate()
            ), */
            /*FunctionalUtil.composeConditional(
                motors.getConfigurator()::applyRampUpPeriod, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Ramp Up Period"), 
                    true,
                    DEFAULT_RAMP_UP_PERIOD
                ), 
                FunctionalUtil.hasChangedDoublePredicate()
            ), */
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Velocity RPM"),
                    true
                ), 
                () -> RPM.convertFrom(
                    motorVelocityRotPerSecSupplier.getAsDouble(), 
                    RotationsPerSecond
                ),
                FunctionalUtil.hasChangedDoublePredicate()
            ),
            /*FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Voltage"),
                    true
                ), 
                () -> motorVoltageSupplier.getAsDouble(),
                FunctionalUtil.hasChangedDoublePredicate()
            ), */
            DashboardItems.createGainsDashboard(
                KEY_BUILDER.copyExtended("Motor Gains"), 
                true,
                new TalonFXSettingGains(motors.getConfigurator()), 
                List.of(
                    GainItem.createProportional(DEFAULT_SLOT0_P),
                    GainItem.createIntegral(DEFAULT_SLOT0_I),
                    GainItem.createDerivative(DEFAULT_SLOT0_D),
                    GainItem.createVelocity(DEFAULT_SLOT0_V),
                    GainItem.createStatic(DEFAULT_SLOT0_S)
                )
            )
        ));
    }

    @Override
    public String getLoggableName() {
        return LOGGABLE_NAME;
    }

    @Override
    public ControlBox<LauncherControl> getControlBox() {
        return controlBox;
    }

    @Override
    public void beginControlInner(LauncherControl control) {
        // TODO Auto-generated method stub
        UniControllable.super.beginControlInner(control);

        targetRPSSupplier = control.getTargetRPSSupplier(this);
    }

    @Override
    public LauncherImpl getThis() {
        return this;
    }

    @Override
    public Controllable<TalonFXBatchControl> getFirstControllable() {
        return motors;
    }

    @Override
    public TalonFXGroup getFirstInstrument() {
        return motors;
    }

    @Override
    public boolean queryBoolean(LauncherBooleanQuery query) {
        return switch (query) {
            case AT_TARGET_SPEED -> targetRPSSupplier.isPresent() 
                ? motorVelocityRotPerSecSupplier.getAsDouble() >= targetRPSSupplier.get().getAsDouble() * (1 - 0.02)
                : true;
        };
    }

    @Override
    public double getShootVelocityFPS(
        DistanceTargetter targetter, 
        LauncherDomain domain
    ) {
        final double distanceFeet = Feet.convertFrom(targetter.getTargetMeters(), Meters);

        final double robotRelativeYVelocityFPS = FeetPerSecond.convertFrom(
            kinematics.getRobotRelativeSpeeds().vyMetersPerSecond,
            MetersPerSecond
        );

        /**
         * Get the base shoot speed based on the distance to target using linear interpolation.
         */
        final double baseShootVelocityFPS = domain.distanceFeetToMotorFPSMap.get(
            distanceFeet
        );
        
        /**
         * Compensate the shoot speed to account for robot motion in the direction of the target.
         * 
         * delta shoot velocity = -robot relative y velocity / cos(pitch angle)
         */
        final double vCompensationFPS = -robotRelativeYVelocityFPS 
            / Math.cos(Radians.convertFrom(PITCH_ANGLE_DEGREES, Degrees));

        LOGGER.finest(() -> "Shoot speed compensation: " + vCompensationFPS + " feet/sec");        

        return baseShootVelocityFPS 
            + (USE_SHOOT_SPEED_COMPENSATION ? vCompensationFPS : 0) 
            + manualCompensationFPSSupplier.getAsDouble()
        ;
    }
}
