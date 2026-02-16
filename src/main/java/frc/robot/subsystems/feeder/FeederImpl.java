package frc.robot.subsystems.feeder;

import static frc.robot.Constants.FeederConstants.*;
import java.util.List;
import java.util.function.DoubleSupplier;
import static frc.robot.Constants.CHECK_DUTY_CYCLE;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.BofaUtil;
import frc.bofalib.control.Controllable;
import frc.bofalib.control.UniControllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.hardware.motor.talon.TalonFXWrapper;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControl;
import frc.bofalib.generic.music.UniInstrument;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import static frc.robot.Constants.CanIDs.*;

// Colloquially known as The Berlin Wall.
final class FeederImpl extends SubsystemBase implements 
    Feeder, 
    UniControllable<FeederImpl, TalonFXControl, FeederControl>,
    UniInstrument<TalonFXWrapper>
{

    // 1 kraken
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(Feeder.class.getSimpleName());
    private final TalonFXWrapper motor = new TalonFXWrapper(FEEDER_CAN_ID);

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
        motor.getConfigurator()
            .apply(DEFAULT_CURRENT_LIMITS_CONFIGS);
        motor.getConfigurator()
            .apply(DEFAULT_MOTOR_OUTPUT_CONFIGS);

        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            BofaUtil.composeConditional(
                motor.getConfigurator()::applyCurrentLimit, 
                DashboardItems.createDoublePuller(
                    KEY_BUILDER.copyExtendedToString("Current Limit"), 
                    DEFAULT_CURRENT_LIMIT
                ), 
                BofaUtil.hasChangedDoublePredicate())
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
