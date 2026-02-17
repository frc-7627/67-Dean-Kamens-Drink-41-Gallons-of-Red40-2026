package frc.bofalib.generic.hardware.motor.sparkmax;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;

public class SparkMaxBuilder {
    private final SparkMaxWrapperImpl wrapperImpl;

    private SparkMaxBuilder(SparkMaxWrapperImpl wrapperImpl) {
        this.wrapperImpl = wrapperImpl;
    }

    public static SparkMaxBuilder create(int deviceId, MotorType motorType) {
        return new SparkMaxBuilder(new SparkMaxWrapperImpl(deviceId, motorType));
    }

    public SparkMaxBuilder withConfig(
        SparkBaseConfig config,
        ResetMode resetMode,
        PersistMode persistMode
    ) {
        wrapperImpl.getConfigurator().apply(
            config, 
            resetMode, 
            persistMode
        );

        return this;
    }

    public SparkMaxWrapper build() {
        return wrapperImpl;
    }

    public static SparkMaxWrapper createBuilt(int deviceId, MotorType motorType) {
        return create(deviceId, motorType).build();
    }

    public static SparkMaxWrapper createBuiltWithConfig(
        int deviceId, 
        MotorType motorType,
        SparkBaseConfig config,
        ResetMode resetMode,
        PersistMode persistMode
    ) {
        return 
            create(deviceId, motorType)
            .withConfig(config, resetMode, persistMode)
            .build()
        ;
    }
}
