package frc.robot.commands.drive.manual;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.drivebase.ManualDrivebase;

public class ResetOdometryToInitial extends InstantCommand {
    public ResetOdometryToInitial(ManualDrivebase drivebase) {
        super(drivebase::resetOdometryToInitial, drivebase);
    }
}
