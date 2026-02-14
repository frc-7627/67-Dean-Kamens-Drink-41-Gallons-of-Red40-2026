package frc.bofalib.generic.hardware.motor.talon;

import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import frc.bofalib.generic.hardware.Hardware;

public final class TalonFXWrapper implements 
    Hardware<ControlRequest, TalonFXConfigurator>
{
    private final TalonFX talonFX;
    private ControlRequest currentRequest;

    public TalonFXWrapper(int deviceId) {
        this.talonFX = new TalonFX(deviceId);
    }

    @Override
    public void beginControl(ControlRequest control) {
        this.currentRequest = control;
    }

    @Override
    public void runControl() {
        talonFX.setControl(currentRequest);
    }

    @Override
    public TalonFXConfigurator getConfigurator() {
        return talonFX.getConfigurator();
    }

    Follower getFollower(MotorAlignmentValue motorAlignmentValue) {
        return new Follower(talonFX.getDeviceID(), motorAlignmentValue);
    }
}
