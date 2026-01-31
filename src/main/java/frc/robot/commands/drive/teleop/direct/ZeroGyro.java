package frc.robot.commands.drive.teleop.direct;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.drivebase.DirectDrivebase;

public class ZeroGyro extends RunCommand {
    public ZeroGyro(DirectDrivebase drivebase) {
        super(drivebase::zeroGyro, drivebase);
    }
}
