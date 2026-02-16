package frc.bofalib.music;

public interface Song {
    String getName();

    String getSongsDirectory();

    default String getChrpFilepath() {
        return getSongsDirectory() + "/" + getName() + ".chrp";
    }
}
