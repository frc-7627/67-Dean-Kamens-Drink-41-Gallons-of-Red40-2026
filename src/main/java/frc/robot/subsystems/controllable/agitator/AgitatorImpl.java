package frc.robot.subsystems.controllable.agitator;

import static frc.robot.Constants.CanIDs.AGITATOR_MOTOR_CAN_ID;
import java.util.function.DoubleSupplier;
import static frc.robot.Constants.CHECK_DUTY_CYCLE;
import static frc.robot.Constants.AgitatorConstants.*;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.control.Controllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.control.LoggingControllable;
import frc.bofalib.generic.control.UniControllable;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxBuilder;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxWrapper;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;

// Colloquially known as Edward Hopper
final class AgitatorImpl extends SubsystemBase implements 
    Agitator, 
    UniControllable<AgitatorImpl, SparkMaxControl, AgitatorControl>,
    LoggingControllable<AgitatorControl>
{
    private static final String LOGGABLE_NAME = "Agitator";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final ControlBox<AgitatorControl> controlBox = new ControlBox<>();

    // one neo
    final SparkMaxWrapper motor = SparkMaxBuilder.create(
        "Agitator Motor",
        AGITATOR_MOTOR_CAN_ID,
        MotorType.kBrushless
    ).withConfig(
        new SparkMaxConfig()
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(AMP_LIMIT), 
        ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters
    ).build();

    final DoubleSupplier dutyCycleSupplier = 
    DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Duty Cycle"), 
        DEFAULT_DUTY_CYCLE, 
        CHECK_DUTY_CYCLE
    );

    final DoubleSupplier manualDutyCycleSupplier =
    DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Manual Duty Cycle"), 
        DEFAULT_MANUAL_DUTY_CYCLE, 
        CHECK_DUTY_CYCLE
    );

    @Override
    public String getLoggableName() {
        return LOGGABLE_NAME;
    }

    @Override
    public ControlBox<AgitatorControl> getControlBox() {
        return controlBox;
    }

    @Override
    public AgitatorImpl getThis() {
        return this;
    }

    @Override
    public Controllable<SparkMaxControl> getFirstControllable() {
        return motor;
    }
}
