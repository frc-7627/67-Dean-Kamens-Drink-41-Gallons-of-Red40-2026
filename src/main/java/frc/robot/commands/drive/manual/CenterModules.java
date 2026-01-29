package frc.robot.commands.drive.manual;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.drivebase.ManualDrivebase;

public class CenterModules extends RunCommand {
    public CenterModules(ManualDrivebase drivebase) {
        super(drivebase::centerModules, drivebase);
    }
}
