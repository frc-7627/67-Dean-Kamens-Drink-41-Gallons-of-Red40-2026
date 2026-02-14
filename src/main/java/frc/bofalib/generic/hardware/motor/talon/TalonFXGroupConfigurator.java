package frc.bofalib.generic.hardware.motor.talon;

import java.util.function.Supplier;
import java.util.stream.Stream;
import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
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

    public void applyCurrentLimit(double currentLimit) {
        apply(
            new CurrentLimitsConfigs()
                .withStatorCurrentLimit(currentLimit)
        );
    }

    public void applyRampUpPeriod(double rampUpPeriod) {
        apply(
            new OpenLoopRampsConfigs()
                .withDutyCycleOpenLoopRampPeriod(rampUpPeriod)
        );

        apply(
            new ClosedLoopRampsConfigs()
                .withDutyCycleClosedLoopRampPeriod(rampUpPeriod)  
        );
    }

    public void apply(ClosedLoopRampsConfigs configuration) {
        leaderConfigurator.apply(configuration);
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }

    public void apply(OpenLoopRampsConfigs configuration) {
        leaderConfigurator.apply(configuration);
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }

    public void apply(Slot0Configs configuration) {
        leaderConfigurator.apply(configuration);
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }

    public void apply(CurrentLimitsConfigs configuration) {
        leaderConfigurator.apply(configuration);
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }

    public void apply(TalonFXConfiguration configuration) {
        leaderConfigurator.apply(configuration);
        followerConfiguratorsSupplier.get().forEach(
            followerConfigurator -> followerConfigurator.apply(configuration)
        );
    }
}
