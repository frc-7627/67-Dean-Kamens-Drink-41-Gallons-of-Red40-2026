package frc.robot.setup.teleop;

import java.util.function.DoubleSupplier;
import com.pathplanner.lib.auto.AutoBuilder.TriFunction;
import frc.robot.subsystems.controllable.drivebase.DriveControl;

/**
 * Interface representing the controller for the robot and produces live desired
 * robot motion.
 */
public interface DriverController extends TeleopController {
    DriveControl getInputDriveControl(
        TriFunction<
            DoubleSupplier, 
            DoubleSupplier, 
            DoubleSupplier, 
            DriveControl
        > driveControlFactory
    );

    DriveControl getInputDriveControlDirect(
        QuadFunction<
        DoubleSupplier,
        DoubleSupplier,
        DoubleSupplier,
        DoubleSupplier,
        DriveControl
        > driveControlFactory
    )

    static DriverController create() {
        return new DriverXboxController();
    }
}
