package frc.robot.subsystems.controllable.intake;

import static frc.robot.Constants.CHECK_DUTY_CYCLE;
import static frc.robot.Constants.IntakeConstants.*;
import static frc.robot.Constants.CanIDs.*;
import java.util.function.DoubleSupplier;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.control.Controllable;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.control.BiControllable;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.control.LoggingControllable;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxBuilder;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxWrapper;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXBuilder;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXWrapper;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.music.UniInstrument;

// Colloquially known as Miles at lunch
final class IntakeImpl extends SubsystemBase implements 
    Intake, 
    BiControllable<IntakeImpl, SparkMaxControl, TalonFXControl, IntakeControl>,
    UniInstrument<TalonFXWrapper>,
    LoggingControllable<IntakeControl>
{
    // Neos
    private static final String LOGGABLE_NAME = "Intake";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final ControlBox<IntakeControl> controlBox = new ControlBox<>();

    private final SparkMaxWrapper pivotMotor = SparkMaxBuilder.create(
        "Intake Pivot Motor",
        PIVOT_MOTOR_CAN_ID, 
        MotorType.kBrushless
    ).withConfig(
        new SparkMaxConfig()
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(AMP_LIMIT), 
        ResetMode.kResetSafeParameters, 
        PersistMode.kPersistParameters
    ).build();

    private final TalonFXWrapper intakeMotor = TalonFXBuilder.create(
        "Intake Main Motor", 
        INTAKE_MOTOR_CAN_ID
    ).build();

    final DoubleSupplier intakeDutyCycle = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Intake Duty Cycle"), 
        DEFAULT_INTAKE_DUTY_CYCLE, 
        CHECK_DUTY_CYCLE
    );

    final DoubleSupplier intakeManualDutyCycle = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Intake Manual Duty Cycle"), 
        DEFAULT_MANUAL_DUTY_CYCLE, 
        CHECK_DUTY_CYCLE
    );

    final DoubleSupplier foldDutyCycle = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Fold Duty Cycle"), 
        DEFAULT_FOLD_DUTY_CYCLE, 
        CHECK_DUTY_CYCLE
    );

    @Override
    public String getLoggableName() {
        return LOGGABLE_NAME;
    }

    @Override
    public ControlBox<IntakeControl> getControlBox() {
        return controlBox;
    }

    @Override
    public IntakeImpl getThis() {
        return this;
    }

    @Override
    public Controllable<SparkMaxControl> getFirstControllable() {
        return pivotMotor;
    }

    @Override
    public Controllable<TalonFXControl> getSecondControllable() {
        return intakeMotor;
    }

    @Override
    public TalonFXWrapper getFirstInstrument() {
        return intakeMotor;
    }
}
