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

    public static SparkMaxBuilder create(String name, int deviceId, MotorType motorType) {
        return new SparkMaxBuilder(new SparkMaxWrapperImpl(name, deviceId, motorType));
    }

    public static SparkMaxBuilder mock(String name) {
        return new SparkMaxBuilder(new SparkMaxWrapperMock(name));
    }

    public static SparkMaxBuilder mock(String name, int deviceId, MotorType motorType) {
        return mock(name);
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
        String name,
        int deviceId, 
        MotorType motorType,
        SparkBaseConfig config,
        ResetMode resetMode,
        PersistMode persistMode
    ) {
        return create(
            name,
            deviceId, 
            motorType
        ).withConfig(
            config, 
            resetMode, 
            persistMode
        );
    }

    public static SparkMaxBuilder mockWithConfig(
        String name,
        int deviceId, 
        MotorType motorType,
        SparkBaseConfig config,
        ResetMode resetMode,
        PersistMode persistMode
    ) {
        return mock(
            name,
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
