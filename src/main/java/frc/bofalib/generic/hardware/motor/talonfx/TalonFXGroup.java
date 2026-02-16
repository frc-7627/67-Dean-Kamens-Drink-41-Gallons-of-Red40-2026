package frc.bofalib.generic.hardware.motor.talonfx;

import java.util.List;
import java.util.Objects;
import java.util.function.DoubleSupplier;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.Pair;
import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlSetting;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXGroupQuery;
import frc.bofalib.music.Instrument;
import frc.bofalib.query.DoubleQueryable;

public final class TalonFXGroup extends
    MotorHardware<TalonFXBatchControl, TalonFXCommonConfigurator> 
implements
    DoubleQueryable<TalonFXGroupQuery>,
    Instrument
{
    private final TalonFXWrapper leaderWrapper;
    private final List<TalonFXWrapper> followerWrappers;
    private final TalonFXGroupConfigurator configurator;

    public TalonFXGroup(
        TalonFXConfiguration configuration, 
        TalonFXWrapper leaderWrapper,
        List<Pair<TalonFXWrapper, MotorAlignmentValue>> followerPairs
    ) {
        this.leaderWrapper = Objects.requireNonNull(leaderWrapper);
        this.followerWrappers = Objects.requireNonNull(followerPairs).stream().map(
            pair -> {
                final TalonFXWrapper followerWrapper = Objects.requireNonNull(pair.getFirst());
                followerWrapper.followerWith(
                    leaderWrapper.getFollower(
                        Objects.requireNonNull(pair.getSecond())
                    )
                );
                return pair.getFirst();
            }
        ).toList();
        this.configurator = new TalonFXGroupConfigurator(
            leaderWrapper.getConfigurator(),
            () -> followerPairs
                .stream()
                .map(Pair::getFirst)
                .map(TalonFXWrapper::getConfigurator)
        );
    }

    @Override
    public void addToOrchestra(Orchestra orchestra) {
        Objects.requireNonNull(orchestra);

        leaderWrapper.addToOrchestra(orchestra);
        followerWrappers.forEach(
            followerWrapper -> followerWrapper.addToOrchestra(orchestra)
        );
    }

    @Override
    public void beginControl(TalonFXBatchControl control) {
        leaderWrapper.beginControl(control.getLeaderControl());
        followerWrappers.forEach(
            followerWrapper -> followerWrapper.beginControl(control.getFollowerControl())
        );
    }

    @Override
    public void runControl() {
        leaderWrapper.runControl();
        followerWrappers.forEach(
            TalonFXWrapper::runControl
        );
    }

    @Override
    public void endControl() {
        leaderWrapper.endControl();
        followerWrappers.forEach(
            TalonFXWrapper::endControl
        );
    }

    @Override
    public TalonFXCommonConfigurator getConfigurator() {
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
            return followerWrappers
                .get(query.index().getAsInt())
                .queryDouble(query.query());
        }
    }
}
