package frc.bofalib.generic.music;

import java.util.Collection;
import com.ctre.phoenix6.Orchestra;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public abstract class SongCommand<AvailableSong extends Song> extends Command {
    private final Collection<? extends MusicalSubsystem> musicalSubsystems;
    private final AvailableSong song;

    private final Orchestra orchestra = new Orchestra();

    public SongCommand(
        Collection<? extends MusicalSubsystem> musicalSubsystems,
        AvailableSong song
    ) {
        this.musicalSubsystems = musicalSubsystems;
        this.song = song;

        addRequirements(musicalSubsystems.stream().map(
            musicalSubsystem -> (Subsystem) musicalSubsystem
        ).toList());
    }

    @Override
    public final void initialize() {
        musicalSubsystems.forEach(
            musicalSubsystem -> musicalSubsystem.addToOrchestra(orchestra)
        );

        orchestra.loadMusic(song.getChrpFilepath());
        orchestra.play();
    }

    @Override
    public final void execute() {
        
    }

    @Override
    public final void end(boolean interrupted) {
        orchestra.stop();
        orchestra.clearInstruments();
    }

    @Override
    public final boolean isFinished() {
        return false;
    }
}
