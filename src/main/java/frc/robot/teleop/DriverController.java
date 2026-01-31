package frc.robot.teleop;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.InputSupplier;

public interface DriverController extends TeleopController {
    Supplier<ChassisSpeeds> getInput(InputSupplier drivebase);

    static DriverController create() {
        return new DriverXboxController();
    }
}
