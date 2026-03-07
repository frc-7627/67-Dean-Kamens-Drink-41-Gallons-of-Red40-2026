package frc.bofalib.generic.hardware.motor.talonfx.gains;

import com.ctre.phoenix6.configs.Slot0Configs;
import frc.bofalib.gains.GainSelection;
import frc.bofalib.gains.Gains;
import frc.bofalib.generic.hardware.motor.talonfx.TalonFXCommonConfigurator;

public final class TalonFXMotionGains implements Gains {
    private final TalonFXCommonConfigurator configurator;

    public TalonFXMotionGains(
        TalonFXCommonConfigurator configurator
    ) {
        this.configurator = configurator;
    }

    @Override
    public void setGain(GainSelection gain, double value) {
        Slot0Configs configs = configurator.GetSlot0Configs();

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
