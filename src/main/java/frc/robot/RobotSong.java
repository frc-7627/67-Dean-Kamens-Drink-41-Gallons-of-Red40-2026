package frc.robot;

import frc.bofalib.generic.music.Song;

public enum RobotSong implements Song {
    PLAY_SUS("sus"),
    PLAY_BAD_TO_THE_BONE("Bad To the Bone"),
    PLAY_BLOODY_TEARS("bloodytears"),
    PLAY_BLUE_LOBSTER("BlueLobster"),
    PLAY_HCB("hcb"),
    PLAY_PHOTOGRAPH("photograph"),
    PLAY_RICKROLL("rickroll"),
    PLAY_UNDERGROUND("Underground"),
    PLAY_VSAUSE("vsauce"),
    PLAY_WII_SHOP("Wii Shop");

    private final String name;

    private RobotSong(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
