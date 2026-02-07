package frc.robot.commands.launcher;

import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import frc.robot.subsystems.launcher.Launcher;

public class ShootOut extends FunctionalCommand {
    public ShootOut(Launcher launcher) {
        super(() -> {}, launcher::shootOut, interrupted -> launcher.stop(), () -> false, launcher);
    }
}
