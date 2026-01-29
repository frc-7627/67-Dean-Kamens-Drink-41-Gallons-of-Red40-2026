package frc.robot.teleop.command;

import frc.robot.subsystems.Indicator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.drivebase.Drivebase;

public record CommandContext(Indicator indicator, Drivebase drivebase, Intake intake) {

}
