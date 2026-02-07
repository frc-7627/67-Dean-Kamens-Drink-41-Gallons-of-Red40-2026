package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import frc.robot.subsystems.feeder.Feeder;

public final class FeedIn extends FunctionalCommand {
    public FeedIn(Feeder feeder) {
        super(() -> {}, feeder::feedIn, interrupted -> {
            feeder.stop();
        }, () -> false, feeder);
    }
}
