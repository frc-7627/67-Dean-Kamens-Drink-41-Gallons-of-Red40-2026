package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.bofalib.BofaUtil;

public class MockCommand extends Command {
    public MockCommand(String name) {
        setName(BofaUtil.mockName(name));
    }
}
