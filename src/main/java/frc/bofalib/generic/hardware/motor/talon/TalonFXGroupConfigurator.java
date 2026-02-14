package frc.bofalib.generic.hardware.motor.talon;

import java.util.function.Supplier;
import java.util.stream.Stream;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;

public final class TalonFXGroupConfigurator {
    private final TalonFXConfigurator leaderConfigurator;
    private final Supplier<Stream<TalonFXConfigurator>> followerConfiguratorsSupplier;

    public TalonFXGroupConfigurator(
        TalonFXConfigurator leaderConfigurator,
        Supplier<Stream<TalonFXConfigurator>> followerConfiguratorsSupplier
    ) {
        this.leaderConfigurator = leaderConfigurator;
        this.followerConfiguratorsSupplier = followerConfiguratorsSupplier;
    }

    public void apply(TalonFXConfiguration configuration) {
        leaderConfigurator.apply(configuration);
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }
}
