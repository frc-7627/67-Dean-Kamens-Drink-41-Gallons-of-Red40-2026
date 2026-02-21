package frc.robot.setup.teleop;

import java.util.function.DoubleSupplier;
import frc.robot.subsystems.controllable.drivebase.DriveControl;

/**
 * Interface representing the controller for the robot and produces live desired
 * robot motion.
 */
public interface DriverController extends TeleopController {
    @FunctionalInterface
    public interface DriverInputsFunction {
        DriveControl getInputDriveControl(
            DoubleSupplier xInput, 
            DoubleSupplier yInput, 
            DoubleSupplier rotInput,
            // TODO: Name this parameter
            DoubleSupplier fourthInput
        );
        
    }

    DriveControl getInputDriveControl(
        DriverInputsFunction driveControlFactory
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
