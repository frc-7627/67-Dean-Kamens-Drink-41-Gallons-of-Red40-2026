package frc.bofalib.generic.hardware.motor.talon;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.DoubleSupplier;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.MotorSetting;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControlEmpty;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControlSetting;
import frc.bofalib.generic.hardware.motor.talon.query.TalonFXQuery;
import frc.bofalib.generic.music.Instrument;
import frc.bofalib.query.DoubleQueryable;

public final class TalonFXWrapper extends 
    MotorHardware<TalonFXControl, TalonFXCommonConfigurator>
implements
    DoubleQueryable<TalonFXQuery>,
    Instrument
{
    private final TalonFX talonFX;
    private final OptionalInt trackNumberOptional;
    private final TalonFXWrapperConfigurator configurator;
    private Optional<Follower> followerOptional = Optional.empty();
    private TalonFXControl control = TalonFXControlEmpty.getInstance();

    private TalonFXWrapper(int deviceId, OptionalInt trackNumberOptional) {
        this.talonFX = new TalonFX(deviceId);
        this.trackNumberOptional = trackNumberOptional;
        this.configurator = new TalonFXWrapperConfigurator(talonFX.getConfigurator());

        reset();
    }

    public TalonFXWrapper(int deviceId) {
        this(deviceId, OptionalInt.empty());
    }

    public TalonFXWrapper(int deviceId, int trackNumber) {
        this(deviceId, OptionalInt.of(trackNumber));
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

    void followerWith(Follower follower) {
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
    public void beginControl(TalonFXControl control) {
        this.control = Objects.requireNonNull(control);
    }

    @Override
    public void runControl() {
        control.visit(
            request -> { talonFX.setControl(request.request()); },
            setting -> {
                setting.setting().visit(
                    dutyCycleSupplier -> { talonFX.set(
                        dutyCycleSupplier.getAsDouble()
                    ); },
                    (magnitudeSupplier, unit) -> { talonFX.setControl(new VelocityVoltage(
                        RotationsPerSecond.convertFrom(magnitudeSupplier.getAsDouble(), unit)
                    )); }
                );
            }
        );
    }

    @Override
    public void endControl() {
        reset();

        talonFX.stopMotor();

        this.control = TalonFXControlEmpty.getInstance();
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
    public DoubleSupplier queryDouble(TalonFXQuery query) {
        return switch (Objects.requireNonNull(query)) {
            case ANGULAR_VELOCITY_ROT_PER_SEC -> talonFX.getVelocity()::getValueAsDouble;
        };
    }
    

    Follower getFollower(MotorAlignmentValue motorAlignmentValue) {
        return new Follower(talonFX.getDeviceID(), motorAlignmentValue);
    }
}
