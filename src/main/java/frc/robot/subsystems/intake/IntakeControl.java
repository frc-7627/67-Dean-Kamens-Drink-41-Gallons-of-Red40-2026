package frc.robot.subsystems.intake;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.BofaUtil;
import frc.bofalib.control.BiControl;
import frc.bofalib.generic.hardware.motor.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlEmpty;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlSetting;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControlEmpty;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControlSetting;

public enum IntakeControl implements BiControl<IntakeImpl, SparkMaxControl, TalonFXControl> {
    LOAD(impl -> impl.intakeDutyCycle, false),
    EJECT(impl -> BofaUtil.negativeSupplier(impl.intakeDutyCycle), false),
    LOAD_MANUAL(impl -> impl.intakeManualDutyCycle, false),
    EJECT_MANUAL(impl -> BofaUtil.negativeSupplier(impl.intakeManualDutyCycle), false),
    FOLD_OUT(impl -> impl.foldDutyCycle, 0),
    FOLD_IN(impl -> BofaUtil.negativeSupplier(impl.foldDutyCycle), 0);

    private final Function<IntakeImpl, SparkMaxControl> pivotControlFunction;
    private final Function<IntakeImpl, TalonFXControl> intakeControlFunction;

    private IntakeControl(
        Function<IntakeImpl, DoubleSupplier> pivotDutyCycleFunction, 
        int dummy
    ) {
        this.pivotControlFunction = impl -> new SparkMaxControlSetting(
            new MotorDutyCycle(
                pivotDutyCycleFunction.apply(impl)
            )
        );
        this.intakeControlFunction = impl -> TalonFXControlEmpty.getInstance();
    }

    private IntakeControl(
        Function<IntakeImpl, DoubleSupplier> intakeDutyCycleFunction,
        boolean dummy
    ) {
        this.pivotControlFunction = impl -> SparkMaxControlEmpty.getInstance();
        this.intakeControlFunction = impl -> new TalonFXControlSetting(
            new MotorDutyCycle(
                intakeDutyCycleFunction.apply(impl)
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
