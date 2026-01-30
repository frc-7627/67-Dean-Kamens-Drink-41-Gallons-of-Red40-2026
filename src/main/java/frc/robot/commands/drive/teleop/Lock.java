package frc.robot.commands.drive.teleop;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.drivebase.TeleopDrivebase;

public class Lock extends RunCommand {
    public Lock(TeleopDrivebase drivebase) {
        super(drivebase::lock, drivebase);
    }
}
