package frc.bofalib.generic.music;

import static frc.robot.Constants.Directories.SONGS_DIRECTORY;

public interface Song {
    String getName();

    default String getChrpFilepath() {
        return SONGS_DIRECTORY + "/" + getName() + ".chrp";
    }
}
