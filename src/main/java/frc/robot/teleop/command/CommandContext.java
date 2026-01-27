package frc.robot.teleop.command;

import frc.robot.subsystems.Indicator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.teleop.controller.TeleopDriveInputs;

public record CommandContext(Indicator indicator, SwerveSubsystem drivebase, Intake intake,
        TeleopDriveInputs driveInputs) {

}
