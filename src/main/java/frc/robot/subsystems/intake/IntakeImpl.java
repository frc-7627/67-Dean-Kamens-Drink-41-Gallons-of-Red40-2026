package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.subsystems.util.dashboard.DashboardField;
import frc.robot.subsystems.util.dashboard.MotorSpeed;

// Colloquially known as Miles at lunch
final class IntakeImpl extends SubsystemBase implements Intake {
    // Neos

    private static final String SUBSYSTEM_NAME = IntakeImpl.class.getSimpleName();

    private final SparkMax motor =
            new SparkMax(Constants.CanIDs.PROTOTYPE_MOTOR_CAN_ID, MotorType.kBrushless);

    private final MotorSpeed loadSpeed = new MotorSpeed(SUBSYSTEM_NAME, "Load Speed",
            Constants.IntakeConstants.DEFAULT_LOAD_SPEED);

    private final DashboardField[] dashboardFields = {loadSpeed};

    /**
     * Subsystem for the intake.
     */
    IntakeImpl() {
        SparkMaxConfig motorConfig = new SparkMaxConfig();
        motorConfig.idleMode(IdleMode.kCoast);
        motorConfig.smartCurrentLimit(Constants.IntakeConstants.AMP_LIMIT);

        motor.configure(motorConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        DashboardField.initAll(dashboardFields);
    }

    /**
     * {@inheritDoc}
     * 
     * Sets the speed of the intake motor to the current load speed.
     */
    @Override
    public void load() {
        motor.set(loadSpeed.getInnerValue());
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

    @Override
    public void periodic() {
        DashboardField.updateAll(dashboardFields);
    }
}
