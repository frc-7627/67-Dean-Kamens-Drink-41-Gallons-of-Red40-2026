package frc.robot.subsystems.controllable.climber;

import static frc.robot.Constants.ClimberConstants.*;
import static frc.robot.Constants.CanIDs.*;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.control.LoggingControllable;
import frc.bofalib.generic.control.UniControllable;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXBuilder;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXWrapper;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.music.UniInstrument;
import frc.robot.subsystems.controllable.feeder.FeederControl;

final class ClimberImpl extends SubsystemBase implements
        Climber, UniControllable<ClimberImpl, TalonFXControl, ClimberControl>,
        UniInstrument<TalonFXWrapper>,
        LoggingControllable<ClimberControl> {

    // one kraken
    private static final String LOGGABLE_NAME = "Climber";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final ControlBox<FeederControl> controlBox = new ControlBox<>();

    final TalonFXWrapper motor = TalonFXBuilder.create(
            "Climber Motor",
            CLIMBER_CAN_ID).withConfig(
                    new TalonFXConfiguration()
                            .withCurrentLimits(DEFAULT_CURRENT_LIMITS_CONFIGS)
                            .withMotorOutput(DEFAULT_MOTOR_OUTPUT_CONFIGS))
            .build();

}
