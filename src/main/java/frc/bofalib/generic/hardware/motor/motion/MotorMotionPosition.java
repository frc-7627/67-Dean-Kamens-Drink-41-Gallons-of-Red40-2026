package frc.bofalib.generic.hardware.motor.motion;

import java.util.function.BiConsumer;
import java.util.function.DoubleSupplier;
import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.AngularVelocityUnit;

public record MotorMotionPosition(
    DoubleSupplier magnitudeSupplier,
    AngleUnit unit
) implements MotorMotion {
    @Override
    public void visit(
        BiConsumer<DoubleSupplier, AngleUnit> positionConsumer,
        BiConsumer<DoubleSupplier, AngularVelocityUnit> velocityConsumer
    ) {
        positionConsumer.accept(magnitudeSupplier, unit);
    }
}
