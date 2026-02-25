package frc.robot.subsystems.controllable.intake;

import java.util.function.DoubleSupplier;
import java.util.function.Function;

import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.setting.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlSetting;
import frc.bofalib.loggable.Loggable;
import frc.bofalib.util.FunctionalUtil;

public enum IntakeControl implements UniControl<
    IntakeImpl, 
    TalonFXControl
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
    /*FOLD_OUT(
        "Intake Fold Out",
        Motor.PIVOT_MOTOR, 
        impl -> impl.foldDutyCycle
    ),
    FOLD_IN(
        "Intake Fold In",
        Motor.PIVOT_MOTOR, 
        impl -> FunctionalUtil.negativeSupplier(impl.foldDutyCycle) //TODO: TAKE THIS OUT AT SOME POINT
    ); */


    private final String name;
    private final Function<IntakeImpl, TalonFXControl> intakeControlFunction;

    private IntakeControl(
        String name,
        Function<IntakeImpl, DoubleSupplier> dutyCycleFunction
    ) {
        this.name = name;

        this.intakeControlFunction = 
            impl -> new TalonFXControlSetting(              
                new MotorDutyCycle(
                    dutyCycleFunction.apply(impl)
                )
            );
    } /*this.firstControlFunction = impl -> new SparkMaxControlSetting(
            new MotorMagicMotion(magicMotionFunction.apply(impl))
        ); */

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public TalonFXControl getFirstControl(IntakeImpl target) {
        return intakeControlFunction.apply(target);
    }
}
