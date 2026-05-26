package frc.robot.subsystems.controllable.Endaffector;

import java.util.function.DoubleSupplier;
import java.util.function.Function;

import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.loggable.Loggable;
import frc.bofalib.util.FunctionalUtil;

public enum EndaffectorControl implements UniControl<EndaffectorImpl, SparkMaxControl>, Loggable {

    OUT("Eject Out", impl -> impl.dutyCycleSupplier),
    OUT_MANUAL("Eject Out Manual", impl -> impl.manualDutyCycleSupplier),
    IN("In", impl -> FunctionalUtil.negativeSupplier(
        impl.dutyCycleSupplier
    )),
    IN_MANUAL("In Manual", impl -> FunctionalUtil.negativeSupplier(
        impl.manualDutyCycleSupplier
    ));

    private final String name;
    private final Function<EndaffectorImpl, SparkMaxControl> firstControlFunction;

    EndaffectorControl(String name, Function<EndaffectorImpl, DoubleSupplier> dutyCycleFunction) {
        this.name = name;
        this.firstControlFunction = impl -> impl.motor.getSetDutyCycleControl(
            dutyCycleFunction.apply(impl)
        );
    }

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public SparkMaxControl getFirstControl(EndaffectorImpl endaffectorImpl) {
        return firstControlFunction.apply(endaffectorImpl);
    } 
}
