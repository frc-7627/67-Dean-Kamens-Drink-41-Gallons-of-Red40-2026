package frc.robot.commands;

import java.util.Collection;
import frc.bofalib.generic.music.MusicalSubsystem;
import frc.bofalib.generic.music.SongCommand;
import frc.robot.RobotSong;

public final class RobotSongCommand extends SongCommand<RobotSong> {
    public RobotSongCommand(
        Collection<? extends MusicalSubsystem> musicalSubsystems,
        RobotSong song
    ) {
        super(musicalSubsystems, song);
    }
}
