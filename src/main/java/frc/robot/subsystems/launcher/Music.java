package frc.robot.subsystems.launcher;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Music extends SubsystemBase {
    private final LauncherMotorsInternal internal;

    public Music(LauncherMotorsInternal internal) {
        this.internal = internal;
    }
}
