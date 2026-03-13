package frc.robot.subsystems.controllable.climber;

import static frc.robot.Constants.ClimberConstants.*;

import java.util.function.DoubleSupplier;

import static frc.robot.Constants.CHECK_DUTY_CYCLE;
import static frc.robot.Constants.CanIDs.*;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.control.Controllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.control.LoggingControllable;
import frc.bofalib.generic.control.UniControllable;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXBuilder;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXWrapper;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.music.UniInstrument;

final class ClimberImpl extends SubsystemBase implements
    Climber, UniControllable<ClimberImpl, TalonFXControl, ClimberControl>,
    UniInstrument<TalonFXWrapper>,
    LoggingControllable<ClimberControl> {

    // one kraken
    private static final String LOGGABLE_NAME = "Climber";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final ControlBox<ClimberControl> controlBox = new ControlBox<>();

    final TalonFXWrapper motor = TalonFXBuilder.create(
        "Climber Motor",
        CLIMBER_CAN_ID
    ).withConfig(
        new TalonFXConfiguration()
            .withCurrentLimits(DEFAULT_CURRENT_LIMITS_CONFIGS)
            .withMotorOutput(DEFAULT_MOTOR_OUTPUT_CONFIGS)
    ).build();

    final DoubleSupplier dutyCycleSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Duty Cycle"), 
        DEFAULT_DUTY_CYCLE, 
        CHECK_DUTY_CYCLE
    );

    @Override
    public ClimberImpl getThis() {
        return this;
    }

    public ControlBox<ClimberControl> getControlBox() {
        return controlBox;
    }

    @Override
    public TalonFXWrapper getFirstInstrument() {
        return motor;
    }

    @Override
    public Controllable<TalonFXControl> getFirstControllable() {
        return motor;
    }

    @Override
    public String getLoggableName() {
        return LOGGABLE_NAME;
    }
}
