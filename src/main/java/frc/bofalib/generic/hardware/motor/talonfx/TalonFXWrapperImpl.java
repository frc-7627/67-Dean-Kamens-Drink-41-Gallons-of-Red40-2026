package frc.bofalib.generic.hardware.motor.talonfx;

import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import frc.bofalib.generic.control.BoxControllableDefaultable;
import frc.bofalib.generic.control.DefaultableControlBox;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlEmpty;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlSetting;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXQuery;
import frc.bofalib.generic.loggable.LoggableBase;

final class TalonFXWrapperImpl extends 
    LoggableBase 
implements
    TalonFXWrapper,
    BoxControllableDefaultable<TalonFXControl>
{
    private final TalonFX talonFX;
    private final OptionalInt trackNumberOptional;
    private final TalonFXWrapperConfigurator configurator;
    private Optional<Follower> followerOptional = Optional.empty();
    private final DefaultableControlBox<TalonFXControl> controlBox = new DefaultableControlBox<>(
        TalonFXControlEmpty.getInstance()
    );

    TalonFXWrapperImpl(String name, int deviceId, OptionalInt trackNumberOptional) {
        super(name);
        this.talonFX = new TalonFX(deviceId);
        this.trackNumberOptional = trackNumberOptional;
        this.configurator = new TalonFXWrapperConfigurator(name, talonFX.getConfigurator());

        reset();
    }

    private void reset() {
        followerOptional.ifPresentOrElse(
            follower -> talonFX.setControl(follower),
            () -> talonFX.setControl(
                new MotionMagicVoltage(
                    talonFX.getPosition().getValueAsDouble()
                )
            )
        );
    }

    @Override
    public DefaultableControlBox<TalonFXControl> getControlBox() {
        return controlBox;
    }

    @Override
    public void followerWith(Follower follower) {
        this.followerOptional = Optional.of(follower);

        reset();
    }

    @Override
    public void addToOrchestra(Orchestra orchestra) {
        Objects.requireNonNull(orchestra);

        trackNumberOptional.ifPresentOrElse(
            trackNumber -> orchestra.addInstrument(talonFX, trackNumber), 
            () -> orchestra.addInstrument(talonFX)
        );
    }

    @Override
    public void runControlInner(TalonFXControl control) {
        control.visit(
            talonFX::setControl,
            setting -> {
                setting.visit(
                    dutyCycleSupplier -> { talonFX.set(
                        dutyCycleSupplier.getAsDouble()
                    ); },
                    (magnitudeSupplier, unit) -> { talonFX.setControl(new VelocityVoltage(
                        RotationsPerSecond.convertFrom(magnitudeSupplier.getAsDouble(), unit)
                    ).withSlot(0)); }
                );
            },
            motion -> {
                motion.visit(
                    (magnitudeSupplier, unit) -> { talonFX.setControl(new MotionMagicVoltage(
                        Rotations.convertFrom(magnitudeSupplier.getAsDouble(), unit)
                    ).withSlot(1)); }, 
                    (magnitudeSupplier, unit) -> { talonFX.setControl(new MotionMagicVelocityVoltage(
                        RotationsPerSecond.convertFrom(magnitudeSupplier.getAsDouble(), unit)
                    ).withSlot(1)); }
                );
            }
        );
    }

    @Override
    public void endControlInner(TalonFXControl control) {
        reset();

        talonFX.stopMotor();
    }

    @Override
    public TalonFXCommonConfigurator getConfigurator() {
        return configurator;
    }

    @Override
    public TalonFXControl getSetControl(MotorSetting motorSetting) {
        return new TalonFXControlSetting(motorSetting);
    }

    @Override
    public double queryDouble(TalonFXQuery query) {
        return switch (Objects.requireNonNull(query)) {
            case ANGULAR_VELOCITY_ROT_PER_SEC -> talonFX.getVelocity().getValueAsDouble();
            case VOLTAGE -> talonFX.getMotorVoltage().getValueAsDouble();
            default -> throw new IllegalArgumentException("Unexpected value: " + Objects.requireNonNull(query));
            
        };
    }
    

    @Override
    public Optional<Follower> getFollower(MotorAlignmentValue motorAlignmentValue) {
        return Optional.of(new Follower(talonFX.getDeviceID(), motorAlignmentValue));
    }
}
