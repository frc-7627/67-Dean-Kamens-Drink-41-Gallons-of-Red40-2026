package frc.robot.subsystems.controllable.swivel;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.control.Controllable;

public interface Swivel extends Subsystem, Controllable<SwivelControl> {
    static Swivel create() {
        return new SwivelImpl();
    }
}
