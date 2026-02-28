package frc.bofalib.generic.hardware.motor.setting;

import static edu.wpi.first.units.Units.RotationsPerSecond;

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
        BiConsumer<DoubleSupplier, AngularVelocityUnit> velocityConsumer
    ) {
        velocityConsumer.accept(magnitudeSupplier, unit);
    }

    @Override
    public String getLoggableName() {
        return "Set Velocity";
    }

    @Override
    public String getLoggableInfo() {
        return getLoggableName() 
            + ": " 
            + RotationsPerSecond.convertFrom(magnitudeSupplier.getAsDouble(), unit)
            + " rot/sec"
        ; 
    }
}
