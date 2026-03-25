package frc.robot.subsystems.controllable.climber;

import java.util.function.DoubleSupplier;
import java.util.function.Function;

import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.loggable.Loggable;
import frc.bofalib.util.FunctionalUtil;

public enum ClimberControl implements UniControl<ClimberImpl, TalonFXControl>, Loggable {
    CLIMB_UP("Climb Up", impl -> impl.dutyCycleSupplier),
    CLIMB_DOWN("Climb Down", impl -> FunctionalUtil.negativeSupplier(impl.dutyCycleSupplier));

    private final String name;
    private final Function<ClimberImpl, TalonFXControl> firstControlFunction;

    ClimberControl(String name, Function<ClimberImpl, DoubleSupplier> dutyCycleFunction) {
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
    public TalonFXControl getFirstControl(ClimberImpl climberImpl) {
        return firstControlFunction.apply(climberImpl);
    }

}
