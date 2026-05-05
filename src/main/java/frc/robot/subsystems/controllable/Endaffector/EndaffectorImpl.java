package frc.robot.subsystems.controllable.Endaffector;

import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxBuilder;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxWrapper;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.control.LoggingControllable;
import frc.bofalib.generic.control.UniControllable;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

final class EndaffectorImpl extends SubsystemBase implements 
Endaffector,
UniControllable<EndaffectorImpl, SparkMaxControl, EndaffectorControl>,
LoggingControllable<EndaffectorControl> 
{
    private static final String LOGGABLE_NAME = "Endaffector";
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of(LOGGABLE_NAME);

    private final ControlBox<EndaffectorControl> controlBox = new ControlBox<>();

    final SparkMaxWrapper motor = SparkMaxBuilder.create(
        "Endaffector Motor",
        ENDAFFECTOR_MOTOR_CAN_ID,
        MotorType.kBrushless
    ).withConfig(
        new SparkMaxConfig()
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(AMP_LIMIT), 
        ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters
    ).build();

    final DoubleSupplier dutyCycleSupplier = 
    DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Duty Cycle"), 
        true,
        DEFAULT_DUTY_CYCLE, 
        CHECK_DUTY_CYCLE
    );

    final DoubleSupplier manualDutyCycleSupplier =
    DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Manual Duty Cycle"), 
        true,
        DEFAULT_MANUAL_DUTY_CYCLE, 
        CHECK_DUTY_CYCLE
    );

    @Override
    public String getLoggableName() {
        return LOGGABLE_NAME;
    }

    @Override
    public ControlBox<EndaffectorControl> getControlBox() {
        return controlBox;
    }

    @Override
    public EndaffectorImpl getThis() {
        return this;
    }

    @Override
    public Controllable<SparkMaxControl> getFirstControllable() {
        return motor;
    }  
}
