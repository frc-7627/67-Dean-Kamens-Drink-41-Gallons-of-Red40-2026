package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robotlib.dashboard.fields.PullingDouble;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import static frc.robot.Constants.CHECK_SIMPLE_MOTOR_SPEED;
import static frc.robot.Constants.CanIDs.PROTOTYPE_MOTOR_CAN_ID;
import static frc.robot.Constants.IntakeConstants.*;

// Colloquially known as Miles at lunch
final class IntakeImpl extends SubsystemBase implements Intake {
    // Neos

    private static final String DASHBOARD_NAME = Intake.class.getSimpleName();

    private final SparkMax motor = new SparkMax(PROTOTYPE_MOTOR_CAN_ID, MotorType.kBrushless);

    private final PullingDouble loadSpeed = new PullingDouble(DASHBOARD_NAME, "Load Speed",
            CHECK_SIMPLE_MOTOR_SPEED, DEFAULT_LOAD_SPEED);

    /**
     * Subsystem for the intake.
     */
    IntakeImpl() {
        final SparkMaxConfig motorConfig = new SparkMaxConfig();
        motorConfig.idleMode(IdleMode.kCoast);
        motorConfig.smartCurrentLimit(AMP_LIMIT);

        motor.configure(motorConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    /**
     * {@inheritDoc}
     * 
     * Sets the speed of the intake motor to the current load speed.
     */
    @Override
    public void load() {
        motor.set(loadSpeed.getPulled());
    }

    /**
     * {@inheritDoc}
     * 
     * Stops the intake motor.
     */
    @Override
    public void stop() {
        motor.stopMotor();
    }
}
