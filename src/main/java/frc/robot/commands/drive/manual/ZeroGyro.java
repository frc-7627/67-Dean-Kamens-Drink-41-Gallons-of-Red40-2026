package frc.robot.commands.drive.manual;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.drivebase.ManualDrivebase;

public class ZeroGyro extends RunCommand {
    public ZeroGyro(ManualDrivebase drivebase) {
        super(drivebase::zeroGyro, drivebase);
    }
}
