package frc.robot.subsystems.controllable.feeder;

import static frc.robot.Constants.FeederConstants.*;
import java.util.List;
import java.util.function.DoubleSupplier;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import static frc.robot.Constants.CHECK_DUTY_CYCLE;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.control.Controllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.gains.GainItem;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.control.LoggingControllable;
import frc.bofalib.generic.control.UniControllable;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXBuilder;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXWrapper;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talonfx.gains.TalonFXSettingGains;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXQuery;
import frc.bofalib.generic.music.UniInstrument;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.bofalib.util.FunctionalUtil;
import static frc.robot.Constants.CanIDs.*;

// Colloquially known as The Berlin Wall.
final class FeederImpl extends SubsystemBase implements 
    Feeder, 
    UniControllable<FeederImpl, TalonFXControl, FeederControl>,
    UniInstrument<TalonFXWrapper>,
    LoggingControllable<FeederControl>
{

    // 1 kraken
    private static final String LOGGABLE_NAME = "Feeder";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final ControlBox<FeederControl> controlBox = new ControlBox<>();

    final TalonFXWrapper motor = TalonFXBuilder.create(
        "Feeder Motor", 
        FEEDER_CAN_ID
        ).withSetVelocityUpdateFreqHz(500)
        .withConfig(
        new TalonFXConfiguration()
            .withCurrentLimits(DEFAULT_CURRENT_LIMITS_CONFIGS)
            .withMotorOutput(DEFAULT_MOTOR_OUTPUT_CONFIGS)
            .withAudio(AUDIO_CONFIGS)
    ).build();

    final DoubleSupplier feedDutyCycleSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Feed Duty Cycle"), 
        true,
        DEFAULT_FEED_SPEED, 
        CHECK_DUTY_CYCLE
    );

    final DoubleSupplier feedManualDutyCycleSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Feed Manual Duty Cycle"), 
        true,
        DEFAULT_FEED_SPEED, 
        CHECK_DUTY_CYCLE
    );

    final DoubleSupplier feedVelocityRotPerSecSupplier = DashboardItems.createDoublePuller(
        KEY_BUILDER.copyExtendedToString("Feed Rot Per Sec"), 
        false, DEAFULT_FEED_ROT_PER_SEC
    );

    private final DoubleSupplier motorVelocityRotPerSecSupplier = () -> motor.queryDouble(
        TalonFXQuery.ANGULAR_VELOCITY_ROT_PER_SEC
    );
    private final DoubleSupplier motorVoltageSupplier = () -> motor.queryDouble(
        TalonFXQuery.VOLTAGE
    );

    FeederImpl() {
        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            FunctionalUtil.composeConditional(
                motor.getConfigurator()::applyCurrentLimit, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Current Limit"), 
                    true,
                    DEFAULT_CURRENT_LIMIT
                ), 
                FunctionalUtil.hasChangedDoublePredicate()
            ),
            // MENTOR CODE RAWR!
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
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Voltage"),
                    true
                ), 
                () -> motorVoltageSupplier.getAsDouble(),
                FunctionalUtil.hasChangedDoublePredicate()
            ),
            DashboardItems.createGainsDashboard(
                KEY_BUILDER.copyExtended("Motor Gains"), 
                false, new TalonFXSettingGains(motor.getConfigurator()), 
                List.of(
                    GainItem.createProportional(DEFAULT_SLOT1_P),
                    GainItem.createIntegral(DEFAULT_SLOT1_I),
                    GainItem.createDerivative(DEFAULT_SLOT1_D),
                    GainItem.createVelocity(DEFAULT_SLOT1_V),
                    GainItem.createStatic(DEFAULT_SLOT1_S)
                )
            )
        ));
        
    }

    @Override
    public String getLoggableName() {
        return LOGGABLE_NAME;
    }

    @Override
    public ControlBox<FeederControl> getControlBox() {
        return controlBox;
    }

    @Override
    public Controllable<TalonFXControl> getFirstControllable() {
        return motor;
    }

    @Override
    public FeederImpl getThis() {
        return this;
    }

    @Override
    public TalonFXWrapper getFirstInstrument() {
        return motor;
    }
}
