package frc.robot.setup.teleop;

import java.util.function.Function;
import frc.robot.subsystems.controllable.drivebase.DriveControl;

/**
 * Interface representing the controller for the robot and produces live desired
 * robot motion.
 */
public interface DriverController extends TeleopController {
    

    DriveControl getInputDriveControl(
        Function<JoystickInputs, DriveControl> driveControlFunction
    );

    static DriverController create() {
        return new DriverXboxController();
    }
}
