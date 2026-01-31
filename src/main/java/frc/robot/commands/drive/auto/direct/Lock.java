package frc.robot.commands.drive.auto.direct;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.drivebase.DirectDrivebase;

public class Lock extends RunCommand {
    public Lock(DirectDrivebase drivebase) {
        super(drivebase::lock, drivebase);
    }
}
