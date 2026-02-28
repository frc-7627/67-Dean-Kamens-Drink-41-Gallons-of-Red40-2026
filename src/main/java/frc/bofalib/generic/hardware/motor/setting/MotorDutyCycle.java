package frc.bofalib.generic.hardware.motor.setting;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import edu.wpi.first.units.AngularVelocityUnit;

public record MotorDutyCycle(
    DoubleSupplier dutyCycleSupplier
) implements MotorSetting {
    @Override
    public void visit(
        Consumer<DoubleSupplier> dutyCycleConsumer,
        BiConsumer<DoubleSupplier, AngularVelocityUnit> velocityConsumer
    ) {
        dutyCycleConsumer.accept(dutyCycleSupplier);
    }

    @Override
    public String getLoggableName() {
        return "Set Duty Cycle";
    }

    @Override
    public String getLoggableInfo() {
        return getLoggableName() 
            + ": " 
            + dutyCycleSupplier.getAsDouble()
        ;
    }
}
