package frc.robot.teleop;

public interface OperatorController extends TeleopController {
    static OperatorController create() {
        return new OperatorXboxController();
    }
}
