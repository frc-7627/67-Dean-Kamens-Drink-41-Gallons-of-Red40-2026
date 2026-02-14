package frc.bofalib.generic.hardware.motor.talon;

import java.util.Collection;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.Pair;
import frc.bofalib.generic.hardware.Hardware;

public final class TalonFXGroup implements
    Hardware<ControlRequest, TalonFXGroupConfigurator> 
{
    private final TalonFXWrapper leaderWrapper;
    private final Collection<Pair<TalonFXWrapper, Follower>> followerPairs;
    private final TalonFXGroupConfigurator configurator;

    public TalonFXGroup(
        TalonFXConfiguration configuration, 
        TalonFXWrapper leaderWrapper,
        Collection<Pair<TalonFXWrapper, MotorAlignmentValue>> followerPairs
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
    }

    @Override
    public void beginControl(ControlRequest control) {
        leaderWrapper.beginControl(control);
        followerPairs.forEach(
            pair -> pair.getFirst().beginControl(pair.getSecond())
        );
    }

    @Override
    public void runControl() {
        leaderWrapper.runControl();
    }

    @Override
    public TalonFXGroupConfigurator getConfigurator() {
        return configurator;
    }
}
