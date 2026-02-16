package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import frc.robot.subsystems.feeder.Feeder;

public final class ManualFeedOut extends FunctionalCommand {
    public ManualFeedOut(Feeder feeder) {
        super(() -> {}, feeder::manualFeedOut, interrupted -> {
            feeder.stop();
        }, () -> false, feeder);
    }
}
