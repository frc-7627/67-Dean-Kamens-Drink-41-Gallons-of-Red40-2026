package frc.bofalib.generic.hardware.motor.sparkmax;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Rotations;
import java.util.Objects;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.bofalib.generic.control.BoxControllableDefaultable;
import frc.bofalib.generic.control.DefaultableControlBox;
import frc.bofalib.generic.hardware.motor.motion.MotorMotion;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlEmpty;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlMotion;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlSetting;
import frc.bofalib.generic.hardware.motor.sparkmax.query.SparkMaxQuery;
import frc.bofalib.generic.loggable.LoggableBase;

final class SparkMaxWrapperImpl extends LoggableBase implements 
    SparkMaxWrapper,
    BoxControllableDefaultable<SparkMaxControl>
{
    private final SparkMax sparkMax;
    private final SparkMaxConfigurator configurator;
    private final DefaultableControlBox<SparkMaxControl> controlBox = new DefaultableControlBox<>(
        SparkMaxControlEmpty.getInstance()
    );

    SparkMaxWrapperImpl(String name, int deviceId, MotorType motorType) {
        super(name);
        this.sparkMax = new SparkMax(deviceId, Objects.requireNonNull(motorType));
        this.configurator = new SparkMaxConfiguratorImpl(name, sparkMax);
    }

    @Override
    public DefaultableControlBox<SparkMaxControl> getControlBox() {
        return controlBox;
    }

    @Override
    public void runControlInner(SparkMaxControl control) {
        control.visit(
            setting -> setting.visit(
                dutyCycleSupplier -> { sparkMax.set(dutyCycleSupplier.getAsDouble()); }, 
                (magnitudeSupplier, unit) -> { sparkMax.getClosedLoopController().setSetpoint(
                    RPM.convertFrom(magnitudeSupplier.getAsDouble(), unit), 
                    ControlType.kVelocity
                ); }
            ), motion -> motion.visit(
                (magnitudeSupplier, unit) -> { sparkMax.getClosedLoopController().setSetpoint(
                    Rotations.convertFrom(magnitudeSupplier.getAsDouble(), unit), 
                    ControlType.kMAXMotionPositionControl
                ); }, (magnitudeSupplier, unit) -> { sparkMax.getClosedLoopController().setSetpoint(
                    RPM.convertFrom(magnitudeSupplier.getAsDouble(), unit), 
                    ControlType.kMAXMotionVelocityControl
                ); }
            )
        );
    }

    @Override
    public double queryDouble(SparkMaxQuery query) {
        return switch (Objects.requireNonNull(query)) {
            case ANGULAR_VELOCITY_ROT_PER_SEC -> sparkMax.getEncoder().getVelocity();
            case VOLTAGE -> sparkMax.getOutputCurrent();
            default -> throw new IllegalArgumentException("Unexpected value: " + Objects.requireNonNull(query));
            
        };
    }

    @Override
    public void endControlInner(SparkMaxControl control) {
        sparkMax.stopMotor();
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

    @Override
    public SparkMaxControl getMotionControl(MotorMotion motorMotion) {
        return new SparkMaxControlMotion(
            Objects.requireNonNull(motorMotion)
        );
    }
}
