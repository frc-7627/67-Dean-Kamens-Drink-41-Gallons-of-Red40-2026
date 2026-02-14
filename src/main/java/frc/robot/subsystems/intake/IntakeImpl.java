package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.robot.Constants.IntakeConstants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import static frc.robot.Constants.CHECK_SIMPLE_MOTOR_SPEED;
import static frc.robot.Constants.CanIDs.LAUNCHER_COMMANDER_CAN_ID;
import static frc.robot.Constants.CanIDs.PROTOTYPE_MOTOR_CAN_ID;
import static frc.robot.Constants.IntakeConstants.*;
import java.util.function.DoubleSupplier;

// Colloquially known as Miles at lunch
final class IntakeImpl extends SubsystemBase implements Intake {
    // Neos
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of("Intake");

    private final SparkMax Neo = new SparkMax(PROTOTYPE_MOTOR_CAN_ID, MotorType.kBrushless);
     private final TalonFX Kraken = new TalonFX(LAUNCHER_COMMANDER_CAN_ID);

    private final DoubleSupplier loadSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Load Speed"), 
        DEFAULT_LOAD_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    /**
     * Subsystem for the intake.
     */
    IntakeImpl() {
        final SparkMaxConfig motorConfig = new SparkMaxConfig();
        motorConfig.idleMode(IdleMode.kCoast);
        motorConfig.smartCurrentLimit(AMP_LIMIT);

        Neo.configure(motorConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    /**
     * {@inheritDoc}
     * 
     * Sets the speed of the intake motor to the current load speed.
     */
    @Override
    public void load() {
        Kraken.set(loadSpeedSupplier.getAsDouble());
    }

    /**
     * {@inheritDoc}
     * 
     * Spins the intake in the opposing direction of the load method
     */
    @Override
    public void eject() {
        Kraken.set(-loadSpeedSupplier.getAsDouble());
    }

    /**
     * {@inheritDoc}
     * Manually spins the intake at a slower speed inwards
     */
    @Override
    public void ManualIn(){
        Kraken.set(IntakeConstants.MANUAL_SPEED);
    }

    /**
     * {@inheritDoc}
     * Manually spins the intakea ta  slower speed outwards
     */
    @Override
    public void ManualOut(){
        Kraken.set(-MANUAL_SPEED);
    }

    /**
     * {@inheritDoc}
     * 
     * Folds the intake out into the ready pos to intake fuel
     */
    @Override
    public void FoldOut(){
        Neo.set(FOLD_SPEED);
        if (Neo.getOutputCurrent() > AMP_LIMIT){
            Neo.stopMotor();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * Folds the intake back inside of the hopper
     */
    @Override
    public void FoldIn(){
        Neo.set(-FOLD_SPEED);
        if (Neo.getOutputCurrent() > AMP_LIMIT){
            Neo.stopMotor();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * Stops the intake motor.
     */
    @Override
    public void stopIntake() {
        Kraken.stopMotor();
    }

    /**
     * {@inheritDoc}
     * 
     * Stops the Swivel 
     */
    @Override
    public void stopSwivel(){
        Neo.stopMotor();
    }

    /**
     * {@inheritDoc}
     * 
     * Stops both motors at once
     */
    @Override
    public void stop(){
        Kraken.stopMotor();
        Neo.stopMotor();
    }

}
