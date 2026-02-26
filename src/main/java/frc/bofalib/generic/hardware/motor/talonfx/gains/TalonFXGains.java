package frc.bofalib.generic.hardware.motor.talonfx.gains;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.SlotConfigs;
import frc.bofalib.gains.GainSelection;
import frc.bofalib.gains.Gains;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXCommonConfigurator;

public final class TalonFXGains implements Gains {
    private final TalonFXCommonConfigurator configurator;
    private final SlotConfigs configs;

    private TalonFXGains(
        TalonFXCommonConfigurator configurator,
        SlotConfigs configs
    ) {
        this.configurator = configurator;
        this.configs = configs;
    }

    public static TalonFXGains createSettingGains(
        TalonFXCommonConfigurator configurator
    ) {
        return new TalonFXGains(configurator, SlotConfigs.from(new Slot0Configs()));
    }

    public static TalonFXGains createMotionGains(
        TalonFXCommonConfigurator configurator
    ) {
        return new TalonFXGains(configurator, SlotConfigs.from(new Slot1Configs()));
    }

    @Override
    public void setGain(GainSelection gain, double value) {
        switch (gain) {
            case PROPORTIONAL -> configs.kP = value;
            case ACCELERATION -> configs.kA = value;
            case DERIVATIVE -> configs.kD = value;
            case GRAVITY -> configs.kG = value;
            case INTEGRAL -> configs.kI = value;
            case STATIC -> configs.kS = value;
            case VELOCITY -> configs.kV = value;
            default -> throw new IllegalArgumentException("Unexpected value: " + gain);
        }

        configurator.apply(configs);
    }
}
