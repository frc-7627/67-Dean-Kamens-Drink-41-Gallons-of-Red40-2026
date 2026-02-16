package frc.bofalib.generic.hardware.motor.talon;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

final class TalonFXGroupConfigurator implements TalonFXCommonConfigurator {
    private final TalonFXCommonConfigurator leaderConfigurator;
    private final Supplier<Stream<TalonFXCommonConfigurator>> followerConfiguratorsSupplier;

    TalonFXGroupConfigurator(
        TalonFXCommonConfigurator leaderConfigurator,
        Supplier<Stream<TalonFXCommonConfigurator>> followerConfiguratorsSupplier
    ) {
        this.leaderConfigurator = leaderConfigurator;
        this.followerConfiguratorsSupplier = followerConfiguratorsSupplier;
    }

    @Override
    public void apply(ClosedLoopRampsConfigs configuration) {
        leaderConfigurator.apply(
            Objects.requireNonNull(configuration)
        );
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }

    @Override
    public void apply(OpenLoopRampsConfigs configuration) {
        leaderConfigurator.apply(
            Objects.requireNonNull(configuration)
        );
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }

    @Override
    public void apply(Slot0Configs configuration) {
        leaderConfigurator.apply(
            Objects.requireNonNull(configuration)
        );
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }

    @Override
    public void apply(CurrentLimitsConfigs configuration) {
        leaderConfigurator.apply(
            Objects.requireNonNull(configuration)
        );
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }

    @Override
    public void apply(TalonFXConfiguration configuration) {
        leaderConfigurator.apply(
            Objects.requireNonNull(configuration)
        );
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }

    @Override
    public void apply(MotorOutputConfigs configuration) {
        leaderConfigurator.apply(
            Objects.requireNonNull(configuration)
        );
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }
}
