package frc.robot.subsystems.controllable.swivel;

import frc.bofalib.generic.hardware.motor.motion.MotorMotionPosition;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlMotion;
import frc.bofalib.loggable.Loggable;
import static edu.wpi.first.units.Units.Degrees;
import java.util.function.DoubleSupplier;
import java.util.function.Function;


import frc.bofalib.generic.control.UniControl;

public enum SwivelControl implements UniControl<
    SwivelImpl,
    SparkMaxControl
>, Loggable{
    FOLD_OUT(
        "Intake Fold Out", 
        impl -> impl.outPositionDegrees
    ),
    FOLD_IN(
        "Intake Fold In", 
        impl -> impl.inPositionDegrees
    );

    private final String name;
    private final Function<SwivelImpl, SparkMaxControl> firstControlFunction;

    SwivelControl(String name, Function<SwivelImpl, DoubleSupplier> positionDegreesFunction) {
        this.name = name;
        this.firstControlFunction = impl -> new SparkMaxControlMotion(new MotorMotionPosition(
            positionDegreesFunction.apply(impl), 
            Degrees
        ));
    }

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public SparkMaxControl getFirstControl(SwivelImpl SwivelImpl) {
        return firstControlFunction.apply(SwivelImpl);
    }
}

