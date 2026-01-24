package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Launcher extends SubsystemBase {

    // 2 krakens

    // Instance variables
    private static double movementSpeed = 0.5;
    private static double ManualSpeed = 0.5;

    private static double rampUpPeriod = 0.5;

    private static double currentLimit = 40;

    // Define Motor IDs
    final TalonFX m_talonFX_Commander = new TalonFX(Constants.CanIDs.LAUNCHER_COMMANDER_CAN_ID);
    final TalonFX m_talonFx_Minion = new TalonFX(Constants.CanIDs.LAUNCHER_MINION_CAN_ID);

    /** Initiallizes the Climber Subsystem */
    public Launcher() {
        // in init function
        var talonFXConfig = new TalonFXConfiguration();

        // Current limit
        talonFXConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
        talonFXConfig.CurrentLimits.withStatorCurrentLimit(currentLimit);

        // Speed limit
        talonFXConfig.MotorOutput.withPeakForwardDutyCycle(movementSpeed);
        talonFXConfig.MotorOutput.withPeakReverseDutyCycle(movementSpeed);

        talonFXConfig.MotorOutput.withNeutralMode(NeutralModeValue.Brake); // Set to brake mode

        // Ramp Up speed
        talonFXConfig.OpenLoopRamps.withDutyCycleOpenLoopRampPeriod(rampUpPeriod);
        talonFXConfig.ClosedLoopRamps.withDutyCycleClosedLoopRampPeriod(rampUpPeriod);

        // Music stuff
        talonFXConfig.Audio.withBeepOnBoot(false);
        talonFXConfig.Audio.withBeepOnConfig(false);
        talonFXConfig.Audio.withAllowMusicDurDisable(true);

        // Set FollowerMode
        // TODO: add a follower mode

        m_talonFX.getConfigurator().apply(talonFXConfig); // Apply Motor Config

    }

    /**
     * Moves the climber up at a speed specified in the instance variable MoveSpeed
     * for general
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void moveUp() {
        m_talonFX.set(movementSpeed);
    }

    /**
     * Moves the climber down at a speed specified in the instance variable
     * MoveSpeed for general
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void moveDown() {
        m_talonFX.set(-movementSpeed);
    }

    /**
     * Slowly moves the climber up at a speed specified in the instance variable
     * ManualSpeed for
     * fine control
     * 
     * @return void
     * @version 1.0
     */
    public void ManualUp() {
        m_talonFX.set(ManualSpeed);
    }

    /**
     * Slowly moves the climber down at a speed specified in the instance variable
     * ManualSpeed for
     * fine control
     * 
     * @return void
     * @version 1.0
     */
    public void ManualDown() {
        m_talonFX.set(-ManualSpeed);
    }

    /**
     * Stops all Motors
     * 
     * @return void
     * @version 1.0
     */
    public void stop() {
        m_talonFX.set(0.0);
    }

    /**
     * Returns the current position of the climber
     * 
     * @return double - the reported encoder position
     * @version 1.0
     */
    public double getPosition() {
        return m_talonFX.getPosition().getValueAsDouble();
    }

    /**
     * Gets the output current of the Endefector Motor
     * 
     * @version 1.0
     * @return Output amperage (double)
     */
    public double getCurrent() {
        return m_talonFX.getSupplyCurrent(false).getValueAsDouble();
    }

    /**
     * Gets the current Endefector Motor Velocity (NOT RPM)
     * 
     * @version 1.0
     * @return Motor Velocity as a double
     */
    public double getVelocity() {
        return m_talonFX.getVelocity().getValueAsDouble();
    }

}
