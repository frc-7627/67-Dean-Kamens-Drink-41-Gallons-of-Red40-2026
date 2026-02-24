package frc.robot.subsystems.controllable.intake;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.generic.control.BiControl;
import frc.bofalib.generic.hardware.motor.setting.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.setting.MotorMagicMotion;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlEmpty;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlEmpty;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlSetting;
import frc.bofalib.loggable.Loggable;
import frc.bofalib.util.FunctionalUtil;

public enum IntakeControl implements BiControl<
    IntakeImpl, 
    SparkMaxControl, 
    TalonFXControl
>, Loggable {
    LOAD(
        "Intake Load",
        Motor.INTAKE_MOTOR, 
        impl -> impl.intakeDutyCycle
    ),
    EJECT(
        "Intake Eject",
        Motor.INTAKE_MOTOR, 
        impl -> FunctionalUtil.negativeSupplier(impl.intakeDutyCycle)
    ),
    LOAD_MANUAL(
        "Intake Load Manual",
        Motor.INTAKE_MOTOR, 
        impl -> impl.intakeManualDutyCycle
    ),
    EJECT_MANUAL(
        "Intake Eject Manual",
        Motor.INTAKE_MOTOR, 
        impl -> FunctionalUtil.negativeSupplier(impl.intakeManualDutyCycle)
    ),
    FOLD_OUT(
        "Intake Fold Out",
        Motor.PIVOT_MOTOR, 
        impl -> impl.foldDutyCycle
    ),
    FOLD_IN(
        "Intake Fold In",
        Motor.PIVOT_MOTOR, 
        impl -> FunctionalUtil.negativeSupplier(impl.foldDutyCycle)
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

    private final String name;
    private final Function<IntakeImpl, SparkMaxControl> pivotControlFunction;
    private final Function<IntakeImpl, TalonFXControl> intakeControlFunction;

    private IntakeControl(
        String name,
        Motor motor,
        Function<IntakeImpl, DoubleSupplier> dutyCycleFunction
    ) {
        this.name = name;
        this.pivotControlFunction = motor.visit(
            impl -> new SparkMaxControlSetting(
                new MotorMagicMotion(
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
    public String getLoggableName() {
        return name;
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
