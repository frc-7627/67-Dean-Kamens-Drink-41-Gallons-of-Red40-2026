package frc.robot.commands.drive.misc;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.controllable.drivebase.MiscDrivebase;

public class Lock extends RunCommand {
    public Lock(MiscDrivebase drivebase) {
        super(drivebase::lock, drivebase);
    }
}
