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
import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.BofaUtil;
import frc.bofalib.control.Controllable;
import frc.bofalib.control.UniControllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXGroup;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXWrapper;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXGroupQuery;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXQuery;
import frc.bofalib.generic.music.UniInstrument;
import frc.bofalib.subsystem.CommandSchedulerWrapper;

// Colloquially known as Miles after bad Chinese
final class LauncherImpl extends SubsystemBase implements 
    Launcher,
    UniControllable<LauncherImpl, TalonFXBatchControl, LauncherControl>,
    UniInstrument<TalonFXGroup>
{

    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of("Launcher");

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

    private final DoubleSupplier motorVelocityRotPerSecSupplier = motors.queryDouble(
        new TalonFXGroupQuery(
            OptionalInt.empty(), 
            TalonFXQuery.ANGULAR_VELOCITY_ROT_PER_SEC
        )
    );

    final DoubleSupplier shootSpeedFPSSupplier =
    DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Shoot Speed Feet Per Sec"), 
        0
    );

    public LauncherImpl() {
        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            BofaUtil.composeConditional(
                motors.getConfigurator()::applyCurrentLimit, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Current Limit"), 
                    DEFAULT_CURRENT_LIMIT
                ), 
                BofaUtil.hasChangedDoublePredicate()
            ),
            BofaUtil.composeConditional(
                motors.getConfigurator()::applyRampUpPeriod, 
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
