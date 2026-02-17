package frc.bofalib.generic.hardware.motor.sparkmax;

import frc.bofalib.generic.hardware.motor.setting.MotorSetting;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControlEmpty;
import frc.bofalib.generic.loggable.MockLoggableBase;

final class SparkMaxWrapperMock extends
    MockLoggableBase
implements 
    SparkMaxWrapper 
{
    SparkMaxWrapperMock(String name) {
        super(name);
    }


    @Override
    public void beginControl(SparkMaxControl control) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void runControl() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endControl() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public SparkMaxConfigurator getConfigurator() {
        return new SparkMaxConfiguratorMock(getLoggableName());
    }

    @Override
    public SparkMaxControl getSetControl(MotorSetting motorSetting) {
        // TODO Auto-generated method stub
        return SparkMaxControlEmpty.getInstance();
    }
    
}
