package frc.bofalib.generic.hardware.motor.talon;

import java.util.List;
import java.util.stream.IntStream;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.Pair;
import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchSong;

public final class TalonFXGroup extends
    MotorHardware<TalonFXBatchControl, TalonFXGroupConfigurator> 
{
    private final TalonFXWrapper leaderWrapper;
    private final List<Pair<TalonFXWrapper, Follower>> followerPairs;
    private final TalonFXGroupConfigurator configurator;
    private final Orchestra orchestra;
    private TalonFXBatchControl control;

    public TalonFXGroup(
        TalonFXConfiguration configuration, 
        TalonFXWrapper leaderWrapper,
        List<Pair<TalonFXWrapper, MotorAlignmentValue>> followerPairs
    ) {
        this.leaderWrapper = leaderWrapper;
        this.followerPairs = followerPairs.stream().map(
            pair -> Pair.of(
                pair.getFirst(), 
                leaderWrapper.getFollower(
                    pair.getSecond()
                )
            )
        ).toList();
        this.configurator = new TalonFXGroupConfigurator(
            leaderWrapper.getConfigurator(),
            () -> followerPairs
                .stream()
                .map(Pair::getFirst)
                .map(TalonFXWrapper::getConfigurator)
        );
        this.orchestra = new Orchestra();
    }

    @Override
    public void beginControl(TalonFXBatchControl control) {
        this.control = control;
        leaderWrapper.beginControl(control.getLeaderControl());

        IntStream.range(0, followerPairs.size()).forEach(
            i -> {
                final Pair<TalonFXWrapper, Follower> followerPair = followerPairs.get(i);
                final TalonFXWrapper wrapper = followerPair.getFirst();
                wrapper.beginControl(control.getFollowerControl(i));
            }
        );

        if (control instanceof TalonFXBatchSong batchSong) {
            orchestra.loadMusic(batchSong.song().getChrpFilepath());
            orchestra.play();
        }
    }

    @Override
    public void runControl() {
        leaderWrapper.runControl();
        followerPairs.forEach(
            followerPair -> followerPair.getFirst().runControl()
        );
    }

    @Override
    public void endControl() {
        if (control instanceof TalonFXBatchSong) {
            orchestra.stop();
            orchestra.clearInstruments();
        }

        leaderWrapper.endControl();
        followerPairs.forEach(
            followerPair -> followerPair.getFirst().endControl()
        );
    }

    @Override
    public TalonFXGroupConfigurator getConfigurator() {
        return configurator;
    }
}
