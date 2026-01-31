package frc.robot.commands.drive.teleop.direct;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.drivebase.DirectDrivebase;

public class ZeroGyro extends InstantCommand {
    public ZeroGyro(DirectDrivebase drivebase) {
        super(drivebase::zeroGyro, drivebase);
    }
}
