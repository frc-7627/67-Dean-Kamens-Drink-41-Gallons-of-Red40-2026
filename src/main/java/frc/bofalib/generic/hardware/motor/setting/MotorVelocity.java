package frc.bofalib.generic.hardware.motor.setting;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import edu.wpi.first.units.AngularVelocityUnit;

public record MotorVelocity(
    DoubleSupplier magnitudeSupplier, 
    AngularVelocityUnit unit
) implements MotorSetting {
    @Override
    public void visit(
        Consumer<DoubleSupplier> dutyCycleConsumer,
        BiConsumer<DoubleSupplier, AngularVelocityUnit> velocityConsumer,
        Consumer<DoubleSupplier> magicMotionConsumer
    ) {
        velocityConsumer.accept(magnitudeSupplier, unit);
    }
}
