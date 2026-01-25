package frc.robot.subsystems.launcher;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Music extends SubsystemBase {
    public static enum Song {
        ;

        private final String filePath;

        Song(String simpleFileName) {
            this.filePath = String.format("%s/%s.chrp", Constants.Directories.SONGS_DIRECTORY, simpleFileName);
        }

        public String getFilePath() {
            return filePath;
        }
    }

    private final LauncherMotorsInternal internal;

    public Music(LauncherMotorsInternal internal) {
        this.internal = internal;
    }

    public void playNote(int freq) {
        internal.playNote(freq);
    }

    public void playSong(Song song) {
        internal.playSongFromFile(song.getFilePath());
    }

    public void reset() {
        internal.reset();
    }
}
