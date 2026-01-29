package frc.robot.commands.drive.manual;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.drivebase.ManualDrivebase;

public class Lock extends RunCommand {
    public Lock(ManualDrivebase drivebase) {
        super(drivebase::lock, drivebase);
    }
}
