package frc.bofalib.generic.hardware.motor.talonfx;

import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import frc.bofalib.generic.hardware.motor.MotorConfigurator;

public interface TalonFXCommonConfigurator extends MotorConfigurator {

    Slot0Configs configs = new Slot0Configs();

    default void applyCurrentLimit(double currentLimit) {
        apply(
            new CurrentLimitsConfigs()
                .withStatorCurrentLimit(currentLimit)
        );
    }

    default void applyRampUpPeriod(double rampUpPeriod) {
        apply(
            new OpenLoopRampsConfigs()
                .withDutyCycleOpenLoopRampPeriod(rampUpPeriod)
        );

        apply(
            new ClosedLoopRampsConfigs()
                .withDutyCycleClosedLoopRampPeriod(rampUpPeriod)  
        );
    }

    void apply(MotorOutputConfigs configuration);

    void apply(ClosedLoopRampsConfigs configuration);

    void apply(OpenLoopRampsConfigs configuration);

    void apply(Slot0Configs configuration);

    void apply(Slot1Configs configuration);

    void apply(CurrentLimitsConfigs configuration);

    void apply(TalonFXConfiguration configuration);

    Slot0Configs GetSlot0Configs();
}
