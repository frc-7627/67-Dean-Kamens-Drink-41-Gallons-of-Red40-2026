package frc.robot.subsystems.controllable.intake;

import java.util.function.DoubleSupplier;
import java.util.function.Function;

import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.bofalib.loggable.Loggable;
import frc.bofalib.util.FunctionalUtil;

public enum IntakeControl implements UniControl<
    IntakeImpl, 
    TalonFXBatchControl
>, Loggable {
    LOAD(
        "Intake Load", 
        impl -> impl.intakeDutyCycle
    ),
    EJECT(
        "Intake Eject",
        impl -> FunctionalUtil.negativeSupplier(impl.intakeDutyCycle)
    ),
    LOAD_MANUAL(
        "Intake Load Manual",
        impl -> impl.intakeManualDutyCycle
    ),
    EJECT_MANUAL(
        "Intake Eject Manual",
        impl -> FunctionalUtil.negativeSupplier(impl.intakeManualDutyCycle)
    );

    private final String name;
    private final Function<IntakeImpl, TalonFXBatchControl> intakeControlFunction;

    private IntakeControl(
        String name,
        Function<IntakeImpl, DoubleSupplier> dutyCycleFunction
    ) {
        this.name = name;

        this.intakeControlFunction = impl -> impl.intakeMotors.getSetDutyCycleControl(
            dutyCycleFunction.apply(impl)
        );
    }

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public TalonFXBatchControl getFirstControl(IntakeImpl target) {
        return intakeControlFunction.apply(target);
    }
}
