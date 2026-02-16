package frc.robot;

import static frc.robot.Constants.Directories.SONGS_DIRECTORY;
import frc.bofalib.music.Song;

public enum RobotSong implements Song {
    SUS("sus"),
    BAD_TO_THE_BONE("Bad To the Bone"),
    BLOODY_TEARS("bloodytears"),
    BLUE_LOBSTER("BlueLobster"),
    HCB("hcb"),
    PHOTOGRAPH("photograph"),
    RICKROLL("rickroll"),
    UNDERGROUND("Underground"),
    VSAUSE("vsauce"),
    WII_SHOP("Wii Shop");

    private final String name;

    private RobotSong(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSongsDirectory() {
        return SONGS_DIRECTORY;
    }
}
