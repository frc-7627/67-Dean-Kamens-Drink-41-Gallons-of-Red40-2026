package frc.bofalib.generic.hardware.motor;

public abstract class ConfiguratorBase implements Configurator {
    private final String loggableMotorName;

    protected ConfiguratorBase(String loggableMotorName) {
        this.loggableMotorName = loggableMotorName;
    }

    @Override
    public String getLoggableMotorName() {
        return loggableMotorName;
    }
}
