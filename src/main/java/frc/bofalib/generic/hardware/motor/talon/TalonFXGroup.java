package frc.bofalib.generic.hardware.motor.talon;

import java.util.List;
import java.util.Objects;
import java.util.function.DoubleSupplier;
import java.util.stream.IntStream;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.Pair;
import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.MotorSetting;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchEmpty;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchSetting;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXBatchSong;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControlSetting;
import frc.bofalib.generic.hardware.motor.talon.query.TalonFXGroupQuery;
import frc.bofalib.query.DoubleQueryable;

public final class TalonFXGroup extends
    MotorHardware<TalonFXBatchControl, TalonFXGroupConfigurator> 
implements
    DoubleQueryable<TalonFXGroupQuery>
{
    private final TalonFXWrapper leaderWrapper;
    private final List<Pair<TalonFXWrapper, Follower>> followerPairs;
    private final TalonFXGroupConfigurator configurator;
    private final Orchestra orchestra;
    private TalonFXBatchControl control = TalonFXBatchEmpty.getInstance();

    public TalonFXGroup(
        TalonFXConfiguration configuration, 
        TalonFXWrapper leaderWrapper,
        List<Pair<TalonFXWrapper, MotorAlignmentValue>> followerPairs
    ) {
        this.leaderWrapper = Objects.requireNonNull(leaderWrapper);
        this.followerPairs = Objects.requireNonNull(followerPairs).stream().map(
            pair -> Pair.of(
                Objects.requireNonNull(pair).getFirst(), 
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
        this.control = Objects.requireNonNull(control);
        leaderWrapper.beginControl(control.getLeaderControl(orchestra));

        IntStream.range(0, followerPairs.size()).forEach(
            i -> {
                final Pair<TalonFXWrapper, Follower> followerPair = followerPairs.get(i);
                final TalonFXWrapper wrapper = followerPair.getFirst();
                wrapper.beginControl(control.getFollowerControl(orchestra, i));
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

        this.control = TalonFXBatchEmpty.getInstance();
    }

    @Override
    public TalonFXGroupConfigurator getConfigurator() {
        return configurator;
    }

    @Override
    public TalonFXBatchControl getSetControl(MotorSetting motorSetting) {
        return new TalonFXBatchSetting(
            new TalonFXControlSetting(
                Objects.requireNonNull(motorSetting)
            )
        );
    }

    @Override
    public DoubleSupplier queryDouble(TalonFXGroupQuery query) {
        Objects.requireNonNull(query);

        if (query.index().isEmpty()) {
            return leaderWrapper.queryDouble(query.query());
        } else {
            return followerPairs
                .get(query.index().getAsInt())
                .getFirst()
                .queryDouble(query.query());
        }
    }
}
