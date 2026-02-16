package frc.bofalib.generic.hardware.motor.talon.control;

import frc.bofalib.generic.music.Song;

public final record TalonFXBatchSong(
    Song song,
    int leaderTrack,
    int[] followerTracks
) implements TalonFXBatchControl {
    @Override
    public TalonFXControl getLeaderControl() {
        return new TalonFXControlTrack();
    }

    @Override
    public TalonFXControl getFollowerControl() {
        return new TalonFXControlTrack();
    }
}
