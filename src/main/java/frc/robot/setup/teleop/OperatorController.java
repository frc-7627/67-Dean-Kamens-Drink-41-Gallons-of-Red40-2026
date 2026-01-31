package frc.robot.setup.teleop;

public interface OperatorController extends TeleopController {
    static OperatorController create() {
        return new OperatorXboxController();
    }
}
