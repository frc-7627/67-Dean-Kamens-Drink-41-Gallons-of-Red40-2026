package frc.bofalib.generic.hardware.motor.talonfx;

import java.util.Optional;
import java.util.function.DoubleSupplier;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControl;
import frc.bofalib.generic.hardware.motor.talonfx.control.TalonFXControlEmpty;
import frc.bofalib.generic.hardware.motor.talonfx.query.TalonFXQuery;
import frc.bofalib.generic.loggable.MockLoggableBase;

final class TalonFXWrapperMock extends 
    MockLoggableBase 
implements 
    TalonFXWrapper 
{

    TalonFXWrapperMock(String name) {
        super(name);
    }

    @Override
    public TalonFXControl getSetControl(MotorSetting motorSetting) {
        // TODO Auto-generated method stub
        return TalonFXControlEmpty.getInstance();
    }

    @Override
    public void beginControl(TalonFXControl control) {
        // TODO Auto-generated method stub
    }

    @Override
    public void runControl() {
        // TODO Auto-generated method stub
    }

    @Override
    public TalonFXCommonConfigurator getConfigurator() {
        // TODO Auto-generated method stub
        return new TalonFXMockConfigurator();
    }

    @Override
    public DoubleSupplier queryDouble(TalonFXQuery query) {
        // TODO Auto-generated method stub
        return () -> 0.0;
    }

    @Override
    public void addToOrchestra(Orchestra orchestra) {
        // TODO Auto-generated method stub
    }

    @Override
    public Optional<Follower> getFollower(MotorAlignmentValue motorAlignmentValue) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public void followerWith(Follower follower) {
        // TODO Auto-generated method stub
    }
        
}
