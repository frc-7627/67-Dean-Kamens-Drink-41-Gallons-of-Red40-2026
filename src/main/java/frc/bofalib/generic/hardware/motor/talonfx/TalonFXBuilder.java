package frc.bofalib.generic.hardware.motor.talonfx;

import java.util.Optional;
import java.util.OptionalInt;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

public final class TalonFXBuilder {
    private final String name;
    private OptionalInt deviceIdOptional = OptionalInt.empty();
    private OptionalInt trackNumberOptional = OptionalInt.empty();
    private Optional<TalonFXConfiguration> configurationOptional = Optional.empty();

    private TalonFXBuilder(String name) {
        this.name = name;
    }

    private TalonFXBuilder(String name, int deviceId) {
        this(name);

        this.deviceIdOptional = OptionalInt.of(deviceId);
    }

    private boolean isMock() {
        return deviceIdOptional.isEmpty();
    }

    public static TalonFXBuilder create(String name, int deviceId) {
        return new TalonFXBuilder(name, deviceId);
    }

    public static TalonFXBuilder createMock(String name) {
        return new TalonFXBuilder(name);
    }

    public static TalonFXBuilder createMock(String name, int deviceId) {
        return createMock(name);
    }

    public TalonFXBuilder withTrackNumber(int trackNumber) {
        this.trackNumberOptional = OptionalInt.of(trackNumber);

        return this;
    }

    public TalonFXBuilder withConfig(TalonFXConfiguration configuration) {
        this.configurationOptional = Optional.of(configuration);

        return this;
    }

    public TalonFXWrapper build() {
        final TalonFXWrapper wrapper = 
            isMock() ? 
            new TalonFXWrapperImpl(
                name, 
                deviceIdOptional.getAsInt(), 
                trackNumberOptional
            ) :
            null
        ;

        configurationOptional.ifPresent(
            wrapper.getConfigurator()::apply
        );

        return wrapper;
    }    
}
