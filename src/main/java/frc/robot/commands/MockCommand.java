package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robotlib.Util;

public class MockCommand extends Command {
    public MockCommand(String name) {
        setName(Util.mockName(name));
    }
}
