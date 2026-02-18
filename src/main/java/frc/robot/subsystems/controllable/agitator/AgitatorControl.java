package frc.robot.subsystems.controllable.agitator;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.util.FunctionalUtil;
import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.setting.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlSetting;
import frc.bofalib.loggable.Loggable;

public enum AgitatorControl implements UniControl<AgitatorImpl, SparkMaxControl>, Loggable {
    AWAY("Agitate Away", impl -> impl.dutyCycleSupplier),
    AWAY_MANUAL("Agitate Away Manual", impl -> impl.manualDutyCycleSupplier),
    TOWARD("Agitate Toward", impl -> FunctionalUtil.negativeSupplier(
        impl.dutyCycleSupplier
    )),
    TOWARD_MANUAL("Agitate Toward Manual", impl -> FunctionalUtil.negativeSupplier(
        impl.manualDutyCycleSupplier
    ));
    
    private final String name;
    private final Function<AgitatorImpl, SparkMaxControl> firstControlFunction;

    AgitatorControl(String name, Function<AgitatorImpl, DoubleSupplier> dutyCycleFunction) {
        this.name = name;
        this.firstControlFunction = impl -> new SparkMaxControlSetting(
            new MotorDutyCycle(
                dutyCycleFunction.apply(impl)
            )
        );
    }

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public SparkMaxControl getFirstControl(AgitatorImpl agitatorImpl) {
        return firstControlFunction.apply(agitatorImpl);
    }
}
