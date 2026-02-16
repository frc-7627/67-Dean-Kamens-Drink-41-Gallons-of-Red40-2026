package frc.bofalib.generic.hardware.motor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import edu.wpi.first.units.AngularVelocityUnit;

public sealed interface MotorSetting permits
    MotorDutyCycle,
    MotorVelocity 
{
    void visit(
        Consumer<DoubleSupplier> dutyCycleConsumer,
        BiConsumer<DoubleSupplier, AngularVelocityUnit> velocityConsumer
    );
}
