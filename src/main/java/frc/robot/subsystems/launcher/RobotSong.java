package frc.robot.subsystems.launcher;

import frc.bofalib.generic.music.Song;

final class RobotSong implements Song {
    private final String name;

    RobotSong(String name) {
        this.name = name;
    }    

    @Override
    public String getName() {
        return name;
    }
}
