package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.bofalib.util.MiscUtil;

public class MockCommand extends Command {
    public MockCommand(String name) {
        setName(MiscUtil.mockName(name));
    }
}
