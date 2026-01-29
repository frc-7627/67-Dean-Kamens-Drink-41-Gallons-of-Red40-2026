package frc.robot.teleop.command;

import frc.robot.subsystems.Indicator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.subsystems.legacy.SwerveSubsystem;

public record CommandContext(Indicator indicator, Drivebase drivebase, Intake intake) {

}
