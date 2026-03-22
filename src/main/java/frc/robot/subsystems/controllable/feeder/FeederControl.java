package frc.robot.subsystems.controllable.feeder;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.loggable.Loggable;
import frc.bofalib.util.FunctionalUtil;

public enum FeederControl implements UniControl<FeederImpl, TalonFXControl>, Loggable {
    FEED_OUT("Feed Out", impl -> impl.feedVelocityRotPerSecSupplier),
    FEED_IN("Feed In", impl -> FunctionalUtil.negativeSupplier(impl.feedVelocityRotPerSecSupplier)),
    FEED_OUT_MANUAL("Feed Out Manual", impl -> impl.feedManualDutyCycleSupplier),
    FEED_IN_MANUAL("Feed In Manual", impl -> FunctionalUtil.negativeSupplier(
        impl.feedManualDutyCycleSupplier
    ));
    
    private final String name;
    private final Function<FeederImpl, TalonFXControl> firstControlFunction;

    FeederControl(String name, Function<FeederImpl, DoubleSupplier> dutyCycleFunction) {
        this.name = name;
        this.firstControlFunction = impl -> impl.motor.getSetVelocityControl(
            dutyCycleFunction.apply(impl),
            RotationsPerSecond
        );
    }

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public TalonFXControl getFirstControl(FeederImpl feederImpl) {
        return firstControlFunction.apply(feederImpl);
    }
}
