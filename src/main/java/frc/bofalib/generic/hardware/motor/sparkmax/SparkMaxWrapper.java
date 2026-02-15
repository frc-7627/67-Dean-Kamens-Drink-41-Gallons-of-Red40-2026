package frc.bofalib.generic.hardware.motor.sparkmax;

import static edu.wpi.first.units.Units.RPM;
import java.util.Objects;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.MotorSetting;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlEmpty;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlSetting;

public final class SparkMaxWrapper extends 
    MotorHardware<SparkMaxControl, SparkMaxConfigurator> 
{
    private final SparkMax sparkMax;
    private final SparkMaxConfigurator configurator;
    private SparkMaxControl control = SparkMaxControlEmpty.getInstance();

    public SparkMaxWrapper(int deviceId, MotorType motorType) {
        this.sparkMax = new SparkMax(deviceId, Objects.requireNonNull(motorType));
        this.configurator = new SparkMaxConfigurator(sparkMax);
    }

    public SparkMaxWrapper(
        int deviceId, 
        MotorType motorType, 
        SparkBaseConfig config,
        ResetMode resetMode,
        PersistMode persistMode
    ) {
        this(deviceId, motorType);

        getConfigurator().apply(config, resetMode, persistMode);
    }

    @Override
    public void beginControl(SparkMaxControl control) {
        this.control = Objects.requireNonNull(control);
    }

    @Override
    public void runControl() {
        if (control instanceof SparkMaxControlSetting setting) {
            setting.setting().visit(
                dutyCycle -> { sparkMax.set(dutyCycle.getDutyCycle()); }, 
                velocity -> { sparkMax.getClosedLoopController().setSetpoint(
                    velocity.getAngularVelocity().in(RPM), 
                    ControlType.kVelocity
                ); }
            );
        }
    }

    @Override
    public void endControl() {
        sparkMax.stopMotor();

        this.control = SparkMaxControlEmpty.getInstance();
    }

    @Override
    public SparkMaxConfigurator getConfigurator() {
        return configurator;
    }

    @Override
    public SparkMaxControl getSetControl(MotorSetting motorSetting) {
        return new SparkMaxControlSetting(
            Objects.requireNonNull(motorSetting)
        );
    }
}
