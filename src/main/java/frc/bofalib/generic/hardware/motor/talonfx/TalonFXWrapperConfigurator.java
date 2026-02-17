package frc.bofalib.generic.hardware.motor.talonfx;

import java.util.Objects;
import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;

final class TalonFXWrapperConfigurator implements TalonFXCommonConfigurator {
    private final String motorName;
    private final TalonFXConfigurator configurator;

    TalonFXWrapperConfigurator(String motorName, TalonFXConfigurator configurator) {
        this.motorName = motorName;
        this.configurator = configurator;
    }

    @Override
    public String getLoggableMotorName() {
        return motorName;
    }

    @Override
    public void apply(ClosedLoopRampsConfigs configuration) {
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }

    @Override
    public void apply(OpenLoopRampsConfigs configuration) {
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }

    @Override
    public void apply(Slot0Configs configuration) {
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }

    @Override
    public void apply(CurrentLimitsConfigs configuration) {
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }

    @Override
    public void apply(MotorOutputConfigs configuration) {
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }

    @Override
    public void apply(TalonFXConfiguration configuration) {
        configurator.apply(
            Objects.requireNonNull(configuration)
        );
    }
}
