package frc.robot.commands.drive.teleop;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.drivebase.TeleopDrivebase;

public class ZeroGyro extends RunCommand {
    public ZeroGyro(TeleopDrivebase drivebase) {
        super(drivebase::zeroGyro, drivebase);
    }
}
