package frc.robot.commands.drive.misc;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.controllable.drivebase.MiscDrivebase;

public class ZeroGyro extends InstantCommand {
    public ZeroGyro(MiscDrivebase drivebase) {
        super(drivebase::zeroGyro, drivebase);
    }
}
