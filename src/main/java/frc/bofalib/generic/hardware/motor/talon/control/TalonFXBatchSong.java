package frc.bofalib.generic.hardware.motor.talon.control;

import com.ctre.phoenix6.Orchestra;
import frc.bofalib.generic.music.Song;

public final record TalonFXBatchSong(
    Song song,
    int leaderTrack,
    int[] followerTracks
) implements TalonFXBatchControl {
    @Override
    public TalonFXControl getLeaderControl(Orchestra orchestra) {
        return new TalonFXControlTrack(orchestra, leaderTrack);
    }

    @Override
    public TalonFXControl getFollowerControl(Orchestra orchestra, int index) {
        if (index < followerTracks.length) {
            return new TalonFXControlTrack(orchestra, followerTracks[index]);
        } else {
            return TalonFXControlEmpty.getInstance();
        }
    }
}
