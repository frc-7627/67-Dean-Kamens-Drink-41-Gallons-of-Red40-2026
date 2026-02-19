package frc.robot.commands.launcher;

import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import frc.robot.subsystems.launcher.Launcher;

public class ManualShootOut extends FunctionalCommand {
    public ManualShootOut(Launcher launcher) {
        super(() -> {}, launcher::manualOut, interrupted -> launcher.stop(), () -> false, launcher);
    }
}
