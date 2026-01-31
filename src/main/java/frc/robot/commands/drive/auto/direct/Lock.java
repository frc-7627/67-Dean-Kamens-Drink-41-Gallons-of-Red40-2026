package frc.robot.commands.drive.auto.direct;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.LoggingWrapperCommand;
import frc.robot.subsystems.drivebase.DirectDrivebase;

public class Lock extends LoggingWrapperCommand {
    public Lock(DirectDrivebase drivebase) {
        super(new RunCommand(drivebase::lock, drivebase));
    }
}
