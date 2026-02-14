package frc.bofalib.generic.hardware.motor;

public sealed interface MotorSetting permits
    MotorDutyCycle,
    MotorVelocity 
{
    
}
