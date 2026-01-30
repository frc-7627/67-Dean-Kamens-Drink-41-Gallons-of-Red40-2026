package frc.robot.subsystems.hopper;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface Hopper extends Subsystem {
    static Hopper create() {
        return new HopperImpl();
    }
}
