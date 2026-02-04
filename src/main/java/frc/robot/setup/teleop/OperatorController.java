package frc.robot.setup.teleop;

/**
 * Interface for the operator controls for robot mechanisms during teleop.
 */
public interface OperatorController extends TeleopController {
    static OperatorController create() {
        return new OperatorXboxController();
    }
}
