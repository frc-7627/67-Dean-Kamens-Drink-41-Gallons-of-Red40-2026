package frc.bofalib.generic.hardware.motor.sparkmax;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;

public class SparkMaxBuilder {
    private final SparkMaxWrapper wrapper;

    private SparkMaxBuilder(SparkMaxWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public static SparkMaxBuilder create(int deviceId, MotorType motorType) {
        return new SparkMaxBuilder(new SparkMaxWrapperImpl(deviceId, motorType));
    }

    public static SparkMaxBuilder mock() {
        return new SparkMaxBuilder(new SparkMaxWrapperMock());
    }

    public static SparkMaxBuilder mock(int deviceId, MotorType motorType) {
        return mock();
    }

    public SparkMaxBuilder withConfig(
        SparkBaseConfig config,
        ResetMode resetMode,
        PersistMode persistMode
    ) {
        wrapper.getConfigurator().apply(
            config, 
            resetMode, 
            persistMode
        );

        return this;
    }

    public static SparkMaxBuilder createWithConfig(
        int deviceId, 
        MotorType motorType,
        SparkBaseConfig config,
        ResetMode resetMode,
        PersistMode persistMode
    ) {
        return create(
            deviceId, 
            motorType
        ).withConfig(
            config, 
            resetMode, 
            persistMode
        );
    }

    public static SparkMaxBuilder mockWithConfig(
        int deviceId, 
        MotorType motorType,
        SparkBaseConfig config,
        ResetMode resetMode,
        PersistMode persistMode
    ) {
        return mock(
            deviceId, 
            motorType
        ).withConfig(
            config, 
            resetMode, 
            persistMode
        );
    }

    public SparkMaxWrapper build() {
        return wrapper;
    }
}
