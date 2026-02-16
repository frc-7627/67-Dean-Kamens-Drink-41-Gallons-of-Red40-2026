package frc.robot.subsystems.controllable.feeder;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.setting.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlSetting;
import frc.bofalib.util.FunctionalUtil;

public enum FeederControl implements UniControl<FeederImpl, TalonFXControl> {
    FEED_OUT(impl -> impl.feedDutyCycleSupplier),
    FEED_IN(impl -> FunctionalUtil.negativeSupplier(impl.feedDutyCycleSupplier)),
    FEED_OUT_MANUAL(impl -> impl.feedManualDutyCycleSupplier),
    FEED_IN_MANUAL(impl -> FunctionalUtil.negativeSupplier(
        impl.feedManualDutyCycleSupplier
    ));
    
    private final Function<FeederImpl, TalonFXControl> firstControlFunction;

    FeederControl(Function<FeederImpl, DoubleSupplier> dutyCycleFunction) {
        this.firstControlFunction = impl -> new TalonFXControlSetting(
                new MotorDutyCycle(dutyCycleFunction.apply(impl))
            );
    }

    @Override
    public TalonFXControl getFirstControl(FeederImpl feederImpl) {
        return firstControlFunction.apply(feederImpl);
    }
}
