package frc.robot.subsystems.controllable.agitator;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.BofaUtil;
import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlSetting;

public enum AgitatorControl implements UniControl<AgitatorImpl, SparkMaxControl> {
    AWAY(impl -> impl.dutyCycleSupplier),
    AWAY_MANUAL(impl -> impl.manualDutyCycleSupplier),
    TOWARD(impl -> BofaUtil.negativeSupplier(
        impl.dutyCycleSupplier
    )),
    TOWARD_MANUAL(impl -> BofaUtil.negativeSupplier(
        impl.manualDutyCycleSupplier
    ));
    
    private final Function<AgitatorImpl, SparkMaxControl> firstControlFunction;

    AgitatorControl(Function<AgitatorImpl, DoubleSupplier> dutyCycleFunction) {
        this.firstControlFunction = impl -> new SparkMaxControlSetting(
            new MotorDutyCycle(
                dutyCycleFunction.apply(impl)
            )
        );
    }

    @Override
    public SparkMaxControl getFirstControl(AgitatorImpl agitatorImpl) {
        return firstControlFunction.apply(agitatorImpl);
    }
}
