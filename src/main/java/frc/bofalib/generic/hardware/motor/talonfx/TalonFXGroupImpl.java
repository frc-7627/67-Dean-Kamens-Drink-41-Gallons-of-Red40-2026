package frc.bofalib.generic.hardware.motor.talonfx;

import java.util.List;
import java.util.Objects;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.Pair;
import frc.bofalib.generic.control.BoxControllable;
import frc.bofalib.generic.control.ControlBox;
import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXBatchControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlMotion;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXGroupQuery;
import frc.bofalib.generic.loggable.LoggableBase;

final class TalonFXGroupImpl extends
    LoggableBase
implements
    TalonFXGroup,
    BoxControllable<TalonFXBatchControl>
{
    private final TalonFXWrapper leaderWrapper;
    private final List<TalonFXWrapper> followerWrappers;
    private final TalonFXGroupConfigurator configurator;
    private final ControlBox<TalonFXBatchControl> controlBox = new ControlBox<>();

    TalonFXGroupImpl(
        String name,
        TalonFXWrapper leaderWrapper,
        List<Pair<TalonFXWrapper, MotorAlignmentValue>> followerPairs
    ) {
        super(name);
        this.leaderWrapper = Objects.requireNonNull(leaderWrapper);
        this.followerWrappers = Objects.requireNonNull(followerPairs).stream().map(
            pair -> {
                Objects.requireNonNull(pair);
                final TalonFXWrapper followerWrapper = Objects.requireNonNull(pair.getFirst());
                leaderWrapper.getFollower(
                    Objects.requireNonNull(pair.getSecond())
                ).ifPresent(
                    followerWrapper::followerWith
                );

                return pair.getFirst();
            }
        ).toList();
        this.configurator = new TalonFXGroupConfigurator(
            name,
            leaderWrapper.getConfigurator(),
            () -> followerPairs
                .stream()
                .map(Pair::getFirst)
                .map(TalonFXWrapper::getConfigurator)
        );
    }

    @Override
    public ControlBox<TalonFXBatchControl> getControlBox() {
        return controlBox;
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
    public void beginControlInner(TalonFXBatchControl control) {
        leaderWrapper.beginControl(control.control());
        followerWrappers.forEach(
            followerWrapper -> followerWrapper.beginControl(control.control())  
        );
    }

    @Override
    public void runControlInner(TalonFXBatchControl control) {
        leaderWrapper.runControl();
        followerWrappers.forEach(
            TalonFXWrapper::runControl 
        );
    }

    @Override
    public void endControlInner(TalonFXBatchControl control) {
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
        return new TalonFXBatchControl(
            new TalonFXControlSetting(
                Objects.requireNonNull(motorSetting)
            )
        );
    }

    @Override
    public TalonFXBatchControl getMotionControl(MotorMotion motorMotion) {
        return new TalonFXBatchControl(
            new TalonFXControlMotion(
                Objects.requireNonNull(motorMotion)
            )
        );
    }

    @Override
    public double queryDouble(TalonFXGroupQuery query) {
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
