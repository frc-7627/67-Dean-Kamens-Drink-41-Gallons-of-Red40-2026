package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.bofalib.BofaUtil;
import frc.bofalib.dashboard.DashboardItems;
import frc.bofalib.dashboard.KeyBuilder;
import frc.bofalib.subsystem.CommandSchedulerWrapper;
import frc.robot.Constants.IntakeConstants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import static frc.robot.Constants.CHECK_SIMPLE_MOTOR_SPEED;
import static frc.robot.Constants.CanIDs.INTAKE_MOTOR_CAN_ID;
import static frc.robot.Constants.CanIDs.PROTOTYPE_MOTOR_CAN_ID;
import static frc.robot.Constants.IntakeConstants.*;
import static edu.wpi.first.units.Units.RPM;

import java.util.List;
import java.util.function.DoubleSupplier;

// Colloquially known as Miles at lunch
final class IntakeImpl extends SubsystemBase implements Intake {
    // Neos
    private static final KeyBuilder KEY_BUILDER = KeyBuilder.of("Intake");

    private final SparkMax Swivel = new SparkMax(PROTOTYPE_MOTOR_CAN_ID, MotorType.kBrushless);
    private final TalonFX Intake = new TalonFX(INTAKE_MOTOR_CAN_ID);

    private final Motors motors = new Motors();

    private final DoubleSupplier loadSpeedSupplier = DashboardItems.createCheckedDoublePuller(
        KEY_BUILDER.copyExtendedToString("Load Speed"), 
        DEFAULT_LOAD_SPEED,
        CHECK_SIMPLE_MOTOR_SPEED
    );

    /**
     * Subsystem for the intake.
     */
    IntakeImpl() {
        CommandSchedulerWrapper.getInstance().registerPeriodicActions(List.of(
            BofaUtil.compose(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Swivel APMS")
                ), 
                () -> Swivel.getOutputCurrent()
            ),
            BofaUtil.compose(
                DashboardItems.createDoublePusher(
                    KEY_BUILDER.copyExtendedToString("Intake RPM")
                ), 
                () -> motors.getIntakeVelocity().in(RPM)
            )
        ));
        final SparkMaxConfig motorConfig = new SparkMaxConfig();
        motorConfig.idleMode(IdleMode.kCoast);
        motorConfig.smartCurrentLimit(AMP_LIMIT);

        Swivel.configure(motorConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    /**
     * {@inheritDoc}
     * 
     * Sets the speed of the intake motor to the current load speed.
     */
    @Override
    public void load() {
        Intake.set(loadSpeedSupplier.getAsDouble());
    }

    /**
     * {@inheritDoc}
     * 
     * Spins the intake in the opposing direction of the load method
     */
    @Override
    public void eject() {
        Intake.set(-loadSpeedSupplier.getAsDouble());
    }

    /**
     * {@inheritDoc}
     * Manually spins the intake at a slower speed inwards
     */
    @Override
    public void manualIn(){
        Intake.set(IntakeConstants.MANUAL_SPEED);
    }

    /**
     * {@inheritDoc}
     * Manually spins the intakea ta  slower speed outwards
     */
    @Override
    public void manualOut(){
        Intake.set(-MANUAL_SPEED);
    }

    /**
     * {@inheritDoc}
     * 
     * Folds the intake out into the ready pos to intake fuel
     */
    @Override
    public void foldOut(){
        Swivel.set(FOLD_SPEED);
        if (Swivel.getOutputCurrent() > AMP_LIMIT){
            Swivel.stopMotor();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * Folds the intake back inside of the hopper
     */
    @Override
    public void foldIn(){
        Swivel.set(-FOLD_SPEED);
        if (Swivel.getOutputCurrent() > AMP_LIMIT){
            Swivel.stopMotor();
        }
    }

    /**
     * Folds the intake In at a slower, manual, rate
     */
    @Override
    public void manualFoldIn(){
        Swivel.set(-MANUAL_FOLD);
        if (Swivel.getOutputCurrent() > AMP_LIMIT){
            Swivel.stopMotor();
        }
    }

    /**
     * Folds the intake Out at a slower, manual, rate
     */
    @Override
    public void manualFoldOut(){
        Swivel.set(MANUAL_FOLD);
        if (Swivel.getOutputCurrent() > AMP_LIMIT){
            Swivel.stopMotor();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * Stops the intake motor.
     */
    @Override
    public void stopIntake() {
        Intake.stopMotor();
    }

    /**
     * {@inheritDoc}
     * 
     * Stops the Swivel 
     */
    @Override
    public void stopSwivel(){
        Swivel.stopMotor();
    }

    /**
     * {@inheritDoc}
     * 
     * Stops both motors at once
     */
    @Override
    public void stop(){
        Intake.stopMotor();
        Swivel.stopMotor();
    }

}
