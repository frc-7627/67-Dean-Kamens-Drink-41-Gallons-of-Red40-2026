package frc.robot.subsystems.controllable.climber;

import java.util.function.Function;

import frc.bofalib.generic.control.UniControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.loggable.Loggable;

public enum ClimberControl implements UniControl<ClimberImpl, TalonFXControl>, Loggable {

    private final String name;
    private final Function<ClimberImpl, TalonFXControl> firstControlFunction;

    // ClimberControl(String name, Function<ClimberImpl, DoubleSupplier> ) {
    // this.name = name;
    // this.firstControlFunction = impl ->
    // }

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public TalonFXControl getFirstControl(ClimberImpl climberImpl) {
        return firstControlFunction.apply(climberImpl);
    }

}
