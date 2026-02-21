package frc.robot.subsystems.controllable.launcher;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static frc.robot.Constants.CanIDs.LAUNCHER_COMMANDER_CAN_ID;
import static frc.robot.Constants.CanIDs.LAUNCHER_MINION_CAN_ID;
import static frc.robot.Constants.LauncherConstants.*;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.DoubleSupplier;
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
import frc.bofalib.generic.hardware.motor.talonfx.gains.Slot0Gains;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXGroupQuery;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXQuery;
import frc.bofalib.generic.music.UniInstrument;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.bofalib.util.FunctionalUtil;

// Colloquially known as Miles after bad Chinese
final class LauncherImpl extends SubsystemBase implements 
    Launcher,
    UniControllable<LauncherImpl, TalonFXBatchControl, LauncherControl>,
    UniInstrument<TalonFXGroup>,
    LoggingControllable<LauncherControl>
{
    private static final String LOGGABLE_NAME = "Launcher";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final ControlBox<LauncherControl> controlBox = new ControlBox<>();

    private final TalonFXGroup motors = TalonFXGroupBuilder.create(
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

    final DoubleSupplier shootSpeedFPSSupplier =
    DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Shoot Feet Per Sec"), 
        DEFAULT_SHOOT_FPS
    );

    final DoubleSupplier activeIdleFPSSupplier =
    DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Active Idle Feet Per Sec"),
        DEFAULT_ACTIVE_IDLE_FPS
    );

    final DoubleSupplier inactiveIdleFPSSupplier =
    DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Inactive Idle Feet Per Sec"), 
        DEFAULT_INACTIVE_IDLE_FPS
    );

    LauncherImpl() {
        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            FunctionalUtil.composeConditional(
                motors.getConfigurator()::applyCurrentLimit, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Current Limit"), 
                    DEFAULT_CURRENT_LIMIT
                ), 
                FunctionalUtil.hasChangedDoublePredicate()
            ),
            FunctionalUtil.composeConditional(
                motors.getConfigurator()::applyRampUpPeriod, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Ramp Up Period"), 
                    DEFAULT_RAMP_UP_PERIOD
                ), 
                FunctionalUtil.hasChangedDoublePredicate()
            ),
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
            DashboardItems.createGainsDashboard(
                KEY_BUILDER.copyExtended("Motor Gains"), 
                new Slot0Gains(motors.getConfigurator()), 
                List.of(
                    GainItem.createProportional(DEFAULT_SLOT0_P),
                    GainItem.createIntegral(DEFAULT_SLOT0_I),
                    GainItem.createDerivative(DEFAULT_SLOT0_D),
                    GainItem.createVelocity(DEFAULT_SLOT0_V)
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
}
