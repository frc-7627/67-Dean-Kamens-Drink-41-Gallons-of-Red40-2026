package frc.robot.setup.teleop;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.drivebase.InputSupplier;

/**
 * Interface representing the controller for the robot and produces live desired
 * robot motion.
 */
public interface DriverController extends TeleopController {
    Supplier<ChassisSpeeds> getInput(InputSupplier drivebase);

    static DriverController create() {
        return new DriverXboxController();
    }
}
