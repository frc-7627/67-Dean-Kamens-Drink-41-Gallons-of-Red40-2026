package frc.robot.subsystems.controllable.feeder;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.BofaUtil;
import frc.bofalib.control.UniControl;
import frc.bofalib.generic.hardware.motor.MotorDutyCycle;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControlSetting;

public enum FeederControl implements UniControl<FeederImpl, TalonFXControl> {
    FEED_OUT(impl -> impl.feedDutyCycleSupplier),
    FEED_IN(impl -> BofaUtil.negativeSupplier(impl.feedDutyCycleSupplier)),
    FEED_OUT_MANUAL(impl -> impl.feedManualDutyCycleSupplier),
    FEED_IN_MANUAL(impl -> BofaUtil.negativeSupplier(
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
