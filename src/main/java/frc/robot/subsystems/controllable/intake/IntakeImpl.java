package frc.robot.subsystems.controllable.intake;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static frc.robot.Constants.CHECK_DUTY_CYCLE;
import static frc.robot.Constants.IntakeConstants.*;
import static frc.robot.Constants.CanIDs.*;

import java.util.function.DoubleSupplier;
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
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXQuery;
import frc.bofalib.generic.music.UniInstrument;
import frc.bofalib.util.FunctionalUtil;

// Colloquially known as Miles at lunch
final class IntakeImpl extends SubsystemBase implements 
    Intake, 
    UniControllable<IntakeImpl, TalonFXControl, IntakeControl>,
    UniInstrument<TalonFXWrapper>,
    LoggingControllable<IntakeControl>
{
    // Neos
    private static final String LOGGABLE_NAME = "Intake";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final ControlBox<IntakeControl> controlBox = new ControlBox<>();


    final TalonFXWrapper intakeMotor = TalonFXBuilder.create(
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

    private final DoubleSupplier motorVelocityRotPerSecSupplier = () -> intakeMotor.queryDouble(
        TalonFXQuery.ANGULAR_VELOCITY_ROT_PER_SEC
    );
    private final DoubleSupplier motorVoltageSupplier = () -> intakeMotor.queryDouble(
        TalonFXQuery.VOLTAGE
    );

    IntakeImpl() {
            // MENTOR CODE RAWR!
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Velocity RPM")
                ), 
                () -> RPM.convertFrom(
                    motorVelocityRotPerSecSupplier.getAsDouble(), 
                    RotationsPerSecond
                ),
                FunctionalUtil.hasChangedDoublePredicate()
            );
            FunctionalUtil.composeConditional(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Motor Voltage")
                ), 
                () -> motorVoltageSupplier.getAsDouble(),
                FunctionalUtil.hasChangedDoublePredicate()
            );
        
    }

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
    public Controllable<TalonFXControl> getFirstControllable() {
        return intakeMotor;
    }

    @Override
    public TalonFXWrapper getFirstInstrument() {
        return intakeMotor;
    }
}
