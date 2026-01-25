package frc.robot.subsystems.launcher;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LauncherMotors extends SubsystemBase {
    private final LauncherMotorsInternal internal;

    public LauncherMotors(LauncherMotorsInternal internal) {
        this.internal = internal;
    }
}
