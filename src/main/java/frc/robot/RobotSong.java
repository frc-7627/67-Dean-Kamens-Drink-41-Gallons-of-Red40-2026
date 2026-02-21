package frc.robot;

import static frc.robot.Constants.Directories.SONGS_DIRECTORY;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
    VSAUCE("vsauce"),
    WII_SHOP("Wii Shop");

    private static final List<RobotSong> SONGS = Collections.unmodifiableList(
        Arrays.asList(values())
    );

    private static final Random RANDOM = new Random();

    /**
     * Get's a random song from the midi folder
     * @return A random chirp file from the midi folder
     */
    public static RobotSong getRandomSong() {
        return SONGS.get(RANDOM.nextInt(SONGS.size()));
    }

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
