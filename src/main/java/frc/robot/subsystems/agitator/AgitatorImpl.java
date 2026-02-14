package frc.robot.subsystems.agitator;

import static frc.robot.Constants.CanIDs.AGITATOR_MOTOR_CAN_ID;
import static frc.robot.Constants.AgitatorConstants.*;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.control.Controllable;
import frc.bofalib.control.UniControllable;
import frc.bofalib.generic.hardware.motor.sparkmax.SparkMaxWrapper;
import frc.bofalib.generic.hardware.motor.sparkmax.control.SparkMaxControl;

// Colloquially known as Edward Hopper
final class AgitatorImpl extends SubsystemBase implements 
    Agitator, 
    UniControllable<SparkMaxControl, AgitatorControl> 
{

    // one neo
    // one time of flight sensor (by playing with fusion)
    private final SparkMaxWrapper motor = new SparkMaxWrapper(
        AGITATOR_MOTOR_CAN_ID,
        MotorType.kBrushless
    );

    AgitatorImpl() {
        motor.getConfigurator().apply(
            new SparkMaxConfig()
                .idleMode(IdleMode.kCoast)
                .smartCurrentLimit(AMP_LIMIT), 
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );
    }

    @Override
    public Controllable<SparkMaxControl> getFirstControllable() {
        return motor;
    }
}
