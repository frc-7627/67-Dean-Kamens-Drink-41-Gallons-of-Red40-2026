package frc.robot.subsystems.controllable.intake;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.BofaUtil;
import frc.bofalib.control.BiControl;
import frc.bofalib.generic.hardware.motor.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlEmpty;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlEmpty;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlSetting;

public enum IntakeControl implements BiControl<
    IntakeImpl, 
    SparkMaxControl, 
    TalonFXControl
> {
    LOAD(
        Motor.INTAKE_MOTOR, 
        impl -> impl.intakeDutyCycle
    ),
    EJECT(
        Motor.INTAKE_MOTOR, 
        impl -> BofaUtil.negativeSupplier(impl.intakeDutyCycle)
    ),
    LOAD_MANUAL(
        Motor.INTAKE_MOTOR, 
        impl -> impl.intakeManualDutyCycle
    ),
    EJECT_MANUAL(
        Motor.INTAKE_MOTOR, 
        impl -> BofaUtil.negativeSupplier(impl.intakeManualDutyCycle)
    ),
    FOLD_OUT(
        Motor.PIVOT_MOTOR, 
        impl -> impl.foldDutyCycle
    ),
    FOLD_IN(
        Motor.PIVOT_MOTOR, 
        impl -> BofaUtil.negativeSupplier(impl.foldDutyCycle)
    );

    private static enum Motor {
        PIVOT_MOTOR,
        INTAKE_MOTOR;

        <Out> Out visit(
            Out pivotMotorOut,
            Out intakeMotorOut
        ) {
            return switch (this) {
                case PIVOT_MOTOR -> pivotMotorOut;
                case INTAKE_MOTOR -> intakeMotorOut;
            };
        }
    }

    private final Function<IntakeImpl, SparkMaxControl> pivotControlFunction;
    private final Function<IntakeImpl, TalonFXControl> intakeControlFunction;

    private IntakeControl(
        Motor motor,
        Function<IntakeImpl, DoubleSupplier> dutyCycleFunction
    ) {
        this.pivotControlFunction = motor.visit(
            impl -> new SparkMaxControlSetting(
                new MotorDutyCycle(
                    dutyCycleFunction.apply(impl)
                )
            ), 
            impl -> SparkMaxControlEmpty.getInstance()
        );

        this.intakeControlFunction = motor.visit(
            impl -> TalonFXControlEmpty.getInstance(), 
            impl -> new TalonFXControlSetting(
                new MotorDutyCycle(
                    dutyCycleFunction.apply(impl)
                )
            )
        );
    }

    @Override
    public SparkMaxControl getFirstControl(IntakeImpl target) {
        return pivotControlFunction.apply(target);
    }

    @Override
    public TalonFXControl getSecondControl(IntakeImpl target) {
        return intakeControlFunction.apply(target);
    }
}
