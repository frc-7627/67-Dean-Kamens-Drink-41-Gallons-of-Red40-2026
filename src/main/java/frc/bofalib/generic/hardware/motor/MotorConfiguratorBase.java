package frc.bofalib.generic.hardware.motor;

public abstract class MotorConfiguratorBase implements MotorConfigurator {
    private final String loggableMotorName;

    protected MotorConfiguratorBase(String loggableMotorName) {
        this.loggableMotorName = loggableMotorName;
    }

    @Override
    public String getLoggableMotorName() {
        return loggableMotorName;
    }
}
