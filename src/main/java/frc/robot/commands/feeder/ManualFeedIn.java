package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import frc.robot.subsystems.feeder.Feeder;

public final class ManualFeedIn extends FunctionalCommand {
    public ManualFeedIn(Feeder feeder) {
        super(() -> {}, feeder::manualFeedIn, interrupted -> {
            feeder.stop();
        }, () -> false, feeder);
    }
}
