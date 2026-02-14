package frc.bofalib.generic.hardware.motor.talon;

import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import frc.bofalib.generic.hardware.motor.MotorHardware;
import frc.bofalib.generic.hardware.motor.talon.control.TalonFXControl;

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
            track -> { track.orchestra().addInstrument(talonFX, track.trackNumber()); }
        );
    }

    @Override
    public void runControl() {
        control.visit(
            request -> { talonFX.setControl(request.request()); }, 
            track -> {}
        );
    }

    @Override
    public TalonFXConfigurator getConfigurator() {
        return talonFX.getConfigurator();
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
