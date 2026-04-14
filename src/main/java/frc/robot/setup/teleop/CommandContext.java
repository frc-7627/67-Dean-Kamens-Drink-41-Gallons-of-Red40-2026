package frc.robot.setup.teleop;

import frc.robot.subsystems.misc.controlstate.ControlStateToggler;
import frc.robot.subsystems.misc.indication.Indicator;
import frc.robot.subsystems.shared.gameinfo.GameInfoSupplier;
import java.util.Collection;
import frc.bofalib.generic.music.MusicalSubsystem;
import frc.robot.subsystems.controllable.drivebase.DriveControl;
import frc.robot.subsystems.controllable.drivebase.Drivebase;

public record CommandContext(
    Indicator indicator,
    Drivebase drivebase,
    ControlStateToggler controlStateToggler,
    GameInfoSupplier gameInfoSupplier,
    DriveControl inputDriveControl,
    Collection<? extends MusicalSubsystem> musicalSubsystems
) {}
