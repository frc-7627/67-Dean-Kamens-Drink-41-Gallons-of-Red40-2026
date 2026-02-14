package frc.bofalib.generic.hardware.motor.talon.control;

import java.util.List;
import frc.bofalib.generic.music.Song;

public final record TalonFXBatchSong(
    Song song,
    TalonFXControlTrack leaderTrack,
    List<TalonFXControlTrack> followerTracks
) implements TalonFXBatchControl {
    @Override
    public TalonFXControl getLeaderControl() {
        return leaderTrack;
    }

    @Override
    public TalonFXControl getFollowerControl(int index) {
        if (index < followerTracks.size()) {
            return followerTracks.get(index);
        } else {
            return TalonFXControlEmpty.getInstance();
        }
    }
}
