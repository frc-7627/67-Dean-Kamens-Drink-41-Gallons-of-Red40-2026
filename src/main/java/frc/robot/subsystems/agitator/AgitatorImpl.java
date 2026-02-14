package frc.robot.subsystems.agitator;

import static frc.robot.Constants.CanIDs.AGITATOR_MOTOR_CAN_ID;
import static frc.robot.Constants.AgitatorConstants.*;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Colloquially known as Edward Hopper
final class AgitatorImpl extends SubsystemBase implements Agitator {

    // one neo
    // one time of flight sensor (by playing with fusion)
    private final SparkMax motor = new SparkMax(
        AGITATOR_MOTOR_CAN_ID, 
        MotorType.kBrushless
    );

    AgitatorImpl() {
        SparkMaxConfig motorConfig = new SparkMaxConfig();
        motorConfig.idleMode(IdleMode.kCoast);
        motorConfig.smartCurrentLimit(AMP_LIMIT);

        motor.configure(
            motorConfig, 
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void toward() {
        motor.set(AGITATOR_SPEED);
    }

    @Override
    public void towardManual() {
        motor.set(MANUAL_AGITATOR_SPEED);
    }

    @Override
    public void away() {
        motor.set(-AGITATOR_SPEED);
    }

    @Override
    public void awayManual() {
        motor.set(-MANUAL_AGITATOR_SPEED);
    }
}
