package frc.bofalib.generic.hardware.motor.talonfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import edu.wpi.first.math.Pair;

public final class TalonFXGroupBuilder {
    private final String name;
    private final Optional<TalonFXWrapper> leaderWrapperOptional;
    private final ArrayList<Pair<
        TalonFXWrapper, 
        MotorAlignmentValue
    >> followerPairs = new ArrayList<>();

    private TalonFXGroupBuilder(String name, Optional<TalonFXWrapper> leaderWrapperOptional) {
        this.name = name;
        this.leaderWrapperOptional = leaderWrapperOptional;
    }

    public static TalonFXGroupBuilder create(String name, TalonFXWrapper leaderWrapper) {
        return new TalonFXGroupBuilder(name, Optional.of(leaderWrapper));
    }

    public static TalonFXGroupBuilder create(String name, TalonFXBuilder leaderBuilder) {
        return create(name, leaderBuilder.build());
    }

    public static TalonFXGroupBuilder mock(String name) {
        return new TalonFXGroupBuilder(name, Optional.empty());
    }

    public static TalonFXGroupBuilder mock(String name, TalonFXWrapper leaderWrapper) {
        return mock(name);
    }

    public static TalonFXGroupBuilder mock(String name, TalonFXBuilder leaderBuilder) {
        return mock(name);
    }

    public TalonFXGroupBuilder withFollower(
        TalonFXWrapper followerWrapper, 
        MotorAlignmentValue followerValue
    ) {
        followerPairs.add(new Pair<>(
            Objects.requireNonNull(followerWrapper), 
            Objects.requireNonNull(followerValue)
        ));

        return this;
    }

    public TalonFXGroupBuilder withFollower(
        TalonFXBuilder followerBuilder, 
        MotorAlignmentValue followerValue
    ) {
        return withFollower(followerBuilder.build(), followerValue);
    }

    public TalonFXGroup build() {
        return leaderWrapperOptional.map(
            leaderWrapper -> (TalonFXGroup) new TalonFXGroupImpl(
                name, 
                leaderWrapper, 
                (List<Pair<
                    TalonFXWrapper, 
                    MotorAlignmentValue
                >>) followerPairs)
        ).orElseGet(() -> new TalonFXGroupMock(name));
    }
}
