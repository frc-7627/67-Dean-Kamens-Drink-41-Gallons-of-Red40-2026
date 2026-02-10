package frc.robot.setup.teleop;

import frc.robot.subsystems.indication.Indicator;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.controlstate.ControlStateToggler;
import frc.robot.subsystems.drivebase.DriveControl;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.subsystems.gameinfo.GameInfoSupplier;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.feeder.Feeder;
import frc.robot.subsystems.hopper.Hopper;

public record CommandContext(
        Indicator indicator,
        Drivebase drivebase,
        Intake intake,
        Launcher launcher,
        Feeder feeder,
        Hopper hopper,
        ControlStateToggler controlStateToggler,
        GameInfoSupplier gameInfoSupplier,
        DriveControl inputControl
) {}
