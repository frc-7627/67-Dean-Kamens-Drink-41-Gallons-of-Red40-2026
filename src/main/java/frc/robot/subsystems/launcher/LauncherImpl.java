package frc.robot.subsystems.launcher;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static frc.robot.Constants.CHECK_DUTY_CYCLE;
import static frc.robot.Constants.CanIDs.LAUNCHER_COMMANDER_CAN_ID;
import static frc.robot.Constants.CanIDs.LAUNCHER_MINION_CAN_ID;
import static frc.robot.Constants.Directories.*;
import static frc.robot.Constants.LauncherConstants.*;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.DoubleSupplier;
import java.util.logging.Logger;
import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.BofaUtil;
import frc.bofalib.control.Controllable;
import frc.bofalib.control.UniControllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.hardware.motor.talon.TalonFXGroup;
import frc.bofalib.generic.hardware.motor.talon.TalonFXWrapper;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talon.query.TalonFXGroupQuery;
import frc.bofalib.generic.hardware.motor.talon.query.TalonFXQuery;
import frc.bofalib.generic.music.Song;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.robot.Constants;

// Colloquially known as Miles after bad Chinese
final class LauncherImpl extends SubsystemBase implements 
    Launcher,
    UniControllable<LauncherImpl, TalonFXBatchControl, LauncherControl> 
{

    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of("Launcher");
    private static final Logger LOGGER = Logger.getLogger(LauncherImpl.class.getName());

    private final TalonFXGroup motors = new TalonFXGroup(
        new TalonFXConfiguration()
            .withCurrentLimits(DEFAULT_CURRENT_LIMITS_CONFIGS)
            .withOpenLoopRamps(DEFAULT_OPEN_LOOP_RAMPS_CONFIGS)
            .withClosedLoopRamps(DEFAULT_CLOSED_LOOP_RAMPS_CONFIGS)
            .withMotorOutput(DEFAULT_MOTOR_OUTPUT_CONFIGS)
            .withAudio(AUDIO_CONFIGS)
            .withSlot0(SLOT0_CONFIGS), 
        new TalonFXWrapper(LAUNCHER_COMMANDER_CAN_ID), 
        List.of(
            Pair.of(new TalonFXWrapper(LAUNCHER_MINION_CAN_ID), MotorAlignmentValue.Aligned)
        )
    );

    public final DoubleSupplier shootSpeedFPSSupplier =
    DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Shoot Speed Feet Per Sec"), 
        0
    );

    private final DoubleSupplier motorVelocityRotPerSecSupplier = motors.queryDouble(
        new TalonFXGroupQuery(
            OptionalInt.empty(), 
            TalonFXQuery.ANGULAR_VELOCITY_ROT_PER_SEC
        )
    );

    public LauncherImpl() {
        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            BofaUtil.composeConditional(
                this::applyCurrentLimit, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Current Limit"), 
                    DEFAULT_CURRENT_LIMIT
                ), 
                BofaUtil.hasChangedDoublePredicate()
            ),
            BofaUtil.composeConditional(
                this::applyRampUpPeriod, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Ramp Up Period"), 
                    DEFAULT_RAMP_UP_PERIOD
                ), 
                BofaUtil.hasChangedDoublePredicate()
            ),
            BofaUtil.compose(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Velocity RPM")
                ), 
                () -> RPM.convertFrom(
                    motorVelocityRotPerSecSupplier.getAsDouble(), 
                    RotationsPerSecond
                )
            )
        ));
    }

    private void applyCurrentLimit(double currentLimit) {
        motors.getConfigurator().apply(
            new CurrentLimitsConfigs()
                .withStatorCurrentLimit(currentLimit)
        );
    }

    private void applyRampUpPeriod(double rampUpPeriod) {
        motors.getConfigurator().apply(
            new OpenLoopRampsConfigs()
                .withDutyCycleOpenLoopRampPeriod(rampUpPeriod)
        );

        motors.getConfigurator().apply(
            new ClosedLoopRampsConfigs()
                .withDutyCycleClosedLoopRampPeriod(rampUpPeriod)  
        );
    }

    @Override
    public LauncherImpl getThis() {
        return this;
    }

    @Override
    public Controllable<TalonFXBatchControl> getFirstControllable() {
        return motors;
    }
}
