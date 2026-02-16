package frc.robot.setup.teleop;

import frc.robot.subsystems.indication.Indicator;
import java.util.Collection;
import frc.bofalib.generic.music.MusicalSubsystem;
import frc.robot.subsystems.controllable.agitator.Agitator;
import frc.robot.subsystems.controllable.drivebase.DriveControl;
import frc.robot.subsystems.controllable.drivebase.Drivebase;
import frc.robot.subsystems.controllable.feeder.Feeder;
import frc.robot.subsystems.controllable.intake.Intake;
import frc.robot.subsystems.controllable.launcher.Launcher;
import frc.robot.subsystems.controlstate.ControlStateToggler;
import frc.robot.subsystems.gameinfo.GameInfoSupplier;

public record CommandContext(
    Indicator indicator,
    Drivebase drivebase,
    Intake intake,
    Launcher launcher,
    Feeder feeder,
    Agitator hopper,
    ControlStateToggler controlStateToggler,
    GameInfoSupplier gameInfoSupplier,
    DriveControl inputDriveControl,
    Collection<? extends MusicalSubsystem> musicalSubsystems
) {}
