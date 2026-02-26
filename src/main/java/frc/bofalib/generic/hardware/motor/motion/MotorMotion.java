package frc.bofalib.generic.hardware.motor.motion;

import java.util.function.BiConsumer;
import java.util.function.DoubleSupplier;
import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.AngularVelocityUnit;

public interface MotorMotion {
    void visit(
        BiConsumer<DoubleSupplier, AngleUnit> positionConsumer,
        BiConsumer<DoubleSupplier, AngularVelocityUnit> velocityConsumer
    );
}
