package frc.bofalib.generic.hardware.motor.talon;

import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.MotorSetting;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControlSetting;

public final class TalonFXWrapper extends 
    MotorHardware<TalonFXControl, TalonFXConfigurator>
{
    private final TalonFX talonFX;
    private TalonFXControl control;

    public TalonFXWrapper(int deviceId) {
        this.talonFX = new TalonFX(deviceId);
    }

    @Override
    public void beginControl(TalonFXControl control) {
        this.control = control;

        control.visit(
            request -> {},
            setting -> {},
            track -> { track.orchestra().addInstrument(talonFX, track.trackNumber()); }
        );
    }

    @Override
    public void runControl() {
        control.visit(
            request -> { talonFX.setControl(request.request()); },
            setting -> {
                setting.setting().visit(
                    dutyCycle -> { talonFX.set(
                        dutyCycle.getDutyCycle()
                    ); },
                    velocity -> { talonFX.setControl(new VelocityVoltage(
                        velocity.getAngularVelocity()
                    )); }
                );
            },
            track -> {}
        );
    }

    @Override
    public TalonFXConfigurator getConfigurator() {
        return talonFX.getConfigurator();
    }

    @Override
    public TalonFXControl getSetControl(MotorSetting motorSetting) {
        return new TalonFXControlSetting(motorSetting);
    }
    

    Follower getFollower(MotorAlignmentValue motorAlignmentValue) {
        return new Follower(talonFX.getDeviceID(), motorAlignmentValue);
    }

    void follow(Follower follower) {
        talonFX.setControl(follower);
    }

    void lead() {
        talonFX.setControl(new MotionMagicVoltage(
            talonFX.getPosition().getValueAsDouble()
        ));
    }
}
