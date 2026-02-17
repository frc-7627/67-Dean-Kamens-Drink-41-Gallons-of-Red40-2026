package frc.robot.subsystems.controllable.feeder;

import static frc.robot.Constants.FeederConstants.*;
import java.util.List;
import java.util.function.DoubleSupplier;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import static frc.robot.Constants.CHECK_DUTY_CYCLE;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.control.Controllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.control.UniControllable;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXBuilder;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXWrapper;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.music.UniInstrument;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.bofalib.util.FunctionalUtil;
import static frc.robot.Constants.CanIDs.*;

// Colloquially known as The Berlin Wall.
final class FeederImpl extends SubsystemBase implements 
    Feeder, 
    UniControllable<FeederImpl, TalonFXControl, FeederControl>,
    UniInstrument<TalonFXWrapper>
{

    // 1 kraken
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(Feeder.class.getSimpleName());
    private final TalonFXWrapper motor = TalonFXBuilder.create(
        "Feeder Motor", 
        FEEDER_CAN_ID
    ).withConfig(
        new TalonFXConfiguration()
            .withCurrentLimits(DEFAULT_CURRENT_LIMITS_CONFIGS)
            .withMotorOutput(DEFAULT_MOTOR_OUTPUT_CONFIGS)
    ).build();

    final DoubleSupplier feedDutyCycleSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Feed Duty Cycle"), 
        DEFAULT_FEED_SPEED, 
        CHECK_DUTY_CYCLE
    );

    final DoubleSupplier feedManualDutyCycleSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Feed Manual Duty Cycle"), 
        DEFAULT_FEED_SPEED, 
        CHECK_DUTY_CYCLE
    );

    FeederImpl() {
        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            FunctionalUtil.composeConditional(
                motor.getConfigurator()::applyCurrentLimit, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Current Limit"), 
                    DEFAULT_CURRENT_LIMIT
                ), 
                FunctionalUtil.hasChangedDoublePredicate())
        ));
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
