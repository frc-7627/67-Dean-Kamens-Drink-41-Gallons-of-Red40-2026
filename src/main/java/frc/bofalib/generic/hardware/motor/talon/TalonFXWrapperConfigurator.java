package frc.bofalib.generic.hardware.motor.talon;

import java.util.Objects;
import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;

public final class TalonFXWrapperConfigurator {
    private final TalonFXConfigurator configurator;

    public TalonFXWrapperConfigurator(TalonFXConfigurator configurator) {
        this.configurator = configurator;
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
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }

    public void apply(OpenLoopRampsConfigs configuration) {
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }

    public void apply(Slot0Configs configuration) {
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }

    public void apply(CurrentLimitsConfigs configuration) {
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }

    public void apply(TalonFXConfiguration configuration) {
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }
}
