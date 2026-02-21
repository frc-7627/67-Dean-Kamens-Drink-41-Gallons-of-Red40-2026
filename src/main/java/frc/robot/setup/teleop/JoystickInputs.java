package frc.robot.setup.teleop;

import java.util.function.DoubleSupplier;

public record JoystickInputs(
    DoubleSupplier leftX,
    DoubleSupplier leftY,
    DoubleSupplier rightX,
    DoubleSupplier rightY
) {
    
}
