package frc.robot.commands.drive.teleop.direct;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.LoggingWrapperCommand;
import frc.robot.subsystems.drivebase.DirectDrivebase;

public class ZeroGyro extends LoggingWrapperCommand {
    public ZeroGyro(DirectDrivebase drivebase) {
        super(new InstantCommand(drivebase::zeroGyro, drivebase));
    }
}
