package frc.robot.teleop.command;

import frc.robot.subsystems.indication.Indicator;
import frc.robot.resources.gameinfo.GameInfoSupplier;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.controlstate.ControlStateToggler;
import frc.robot.subsystems.drivebase.Drivebase;

public record CommandContext(Indicator indicator, Drivebase drivebase, Intake intake,
        ControlStateToggler controlStateToggler, GameInfoSupplier gameInfoSupplier) {

}
