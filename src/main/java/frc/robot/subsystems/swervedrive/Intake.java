package frc.robot.subsystems.swervedrive;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.List;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.subsystems.swervedrive.util.DashboardDouble;
import frc.robot.subsystems.swervedrive.util.DashboardField;

public class Intake extends SubsystemBase {
    // Neos

    private final SparkMax motor =
            new SparkMax(Constants.CanIDs.PROTOTYPE_MOTOR_ID, MotorType.kBrushless);

    private double loadSpeed = Constants.IntakeConstants.DEFAULT_LOAD_SPEED;

    private static final List<DashboardField<Intake, ?>> DASHBOARD_FIELDS =
            List.of(new DashboardField<Intake, DashboardDouble>("Load Speed",
                    (intake) -> new DashboardDouble(intake.loadSpeed), (pair) -> {
                        pair.left().loadSpeed = pair.right().value();
                    }, new DashboardDouble(Constants.IntakeConstants.DEFAULT_LOAD_SPEED), true));

    /**
     * Subsystem for the intake.
     */
    public Intake() {
        SparkMaxConfig motor_config = new SparkMaxConfig();
        motor_config.idleMode(IdleMode.kCoast);
        motor_config.smartCurrentLimit(Constants.IntakeConstants.AMP_LIMIT);

        // TODO: find way to configure that doesn't use deprecated constants.
        motor.configure(motor_config, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    /**
     * Load a gamepiece with the intake.
     * 
     * Sets the speed of the intake motor to the current load speed.
     */
    public void load() {
        motor.set(loadSpeed);
    }

    /**
     * Stops the intake.
     * 
     * Stops the intake motor.
     */
    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void periodic() {
        DashboardField.updateAll(DASHBOARD_FIELDS, this);
    }
}
