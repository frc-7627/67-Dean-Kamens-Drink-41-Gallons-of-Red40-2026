package frc.robot.subsystems;

import static frc.robot.Constants.Directories.SONGS_DIRECTORY;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.launcher.LauncherMotors;

// Colloquially known as Miles after bad Chinese
public class Launcher extends SubsystemBase {

    // 2 krakens

    public static enum Song {
        ;

        private final String filePath;

        Song(String simpleFileName) {
            this.filePath = String.format("%s/%s.chrp", SONGS_DIRECTORY, simpleFileName);
        }
    }

    // Instance variables
    private static double ShootSpeed = Constants.LauncherConstants.ShootSpeed;
    private static double ActiveIdle = Constants.LauncherConstants.ActiveIdle;
    private static double InactiveIdle = Constants.LauncherConstants.InactiveIdle;
    private static double ManualSpeed = Constants.LauncherConstants.ManualSpeed;

    private static double rampUpPeriod = Constants.LauncherConstants.rampUpPeriod; // TODO: Test
                                                                                   // this
                                                                                   // thoroughly

    private static double currentLimit = Constants.LauncherConstants.currentLimit;

    // Define Motor IDs
    final TalonFX m_talonFX_Commander = new TalonFX(Constants.CanIDs.LAUNCHER_COMMANDER_CAN_ID);
    final TalonFX m_talonFX_Minion = new TalonFX(Constants.CanIDs.LAUNCHER_MINION_CAN_ID);

    // Magic motion for audio and follower control
    private final MotionMagicVoltage m_request = new MotionMagicVoltage(0);

    // Make an orchestra
    Orchestra m_Orchestra = new Orchestra();

    private final LauncherMotors launcherMotors;

    /** Initiallizes the Climber Subsystem */
    public Launcher(LauncherMotors launcherMotors) {
        this.launcherMotors = launcherMotors;

        // in init function
        var talonFXConfig = new TalonFXConfiguration();

        // Current limit
        talonFXConfig.CurrentLimits.withStatorCurrentLimitEnable(true);
        talonFXConfig.CurrentLimits.withStatorCurrentLimit(currentLimit);

        // Speed limit
        talonFXConfig.MotorOutput.withPeakForwardDutyCycle(ShootSpeed);
        talonFXConfig.MotorOutput.withPeakReverseDutyCycle(-ShootSpeed);

        talonFXConfig.MotorOutput.withNeutralMode(NeutralModeValue.Coast); // Set to Coast mode bc
                                                                           // big mass flywheel

        // Ramp Up speed
        talonFXConfig.OpenLoopRamps.withDutyCycleOpenLoopRampPeriod(rampUpPeriod);
        talonFXConfig.ClosedLoopRamps.withDutyCycleClosedLoopRampPeriod(rampUpPeriod);

        // Music stuff
        talonFXConfig.Audio.withBeepOnBoot(false);
        talonFXConfig.Audio.withBeepOnConfig(false);
        talonFXConfig.Audio.withAllowMusicDurDisable(true);

        // Set FollowerMode

        m_talonFX_Commander.getConfigurator().apply(talonFXConfig); // Apply Motor Config
        m_talonFX_Minion.getConfigurator().apply(talonFXConfig); // Apply Motor Config

        // Reset controls after using audio IS A MUST OR ELSE WILL EXPLODE
        // Also sets m_talonFX_Minion to follower of m_talonFX_Commander
        resetControlMode();
    }

    /**
     * Plays a constant tone based on provided input using the talonFX controllers
     * 
     * Only use this when elevator is at 0
     * 
     * @param freq the frequency in hz of the tone
     * @return void
     * @version 1.0
     */
    public void playNote(int freq) {
        launcherMotors.playNote(freq);
    }

    /**
     * Plays a CHRP file using Pheonix Orchestra using both TalonFX motor controllers, limited to
     * the amount of talonFXs used by subsystem
     *
     * Only use this when elevator is at 0
     *
     * MIDI files can be converted to CHRP files in the Pheonix Tuner X utilites
     * 
     * @param filename the name of the CHRP file as String (without the extension)
     * @return void
     * @version 1.0
     */
    public void playSong(Song song) {
        launcherMotors.playSongFromFile(song.filePath);
    }

    /**
     * Resets the control modes of both TalonFXs Must use after playing audio on the Motor
     * Controllers To revert them back to position control for elevator use
     * 
     * @return void
     * @version 1.0
     */
    public void resetControlMode() {
        // Clean up orchestra
        m_Orchestra.stop();
        m_Orchestra.clearInstruments();

        // create a Motion Magic request, voltage output
        // final MotionMagicVoltage m_request = new MotionMagicVoltage(0);
        m_talonFX_Commander.setControl(m_request.withPosition(getPositionCommander()));

        // Setup follower config
        m_talonFX_Minion.setControl(new Follower(m_talonFX_Commander.getDeviceID(), null));
    }


    /**
     * Spins the Launcher up at a speed specified in the instance variable ShootSpeed for general
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void ShootOut() {
        m_talonFX_Commander.set(ShootSpeed);
    }

    /**
     * Spins the Launcher in a counter clockwise direction at a speed specified in the instance
     * variable ShootSpeed for general TODO: make sure ^^ this is the right way control
     * 
     * @return void
     * @version 1.0
     */
    public void ShootIn() {
        m_talonFX_Commander.set(-ShootSpeed); // DO NOT USE UNLESS IN AN EXTRENUOUS CIRCUMSTANCE
    }

    /**
     * Slowly moves the launcher at a speed specified in the instance variable ManualSpeed for fine
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void ManualOutBoth() {
        m_talonFX_Commander.set(ManualSpeed);
        m_talonFX_Minion.set(ManualSpeed);
    }

    /**
     * Slowly moves the launcher at a speed specified in the instance variable ManualSpeed for fine
     * control
     * 
     * @return void
     * @version 1.0
     */
    public void ManualInBoth() {
        m_talonFX_Commander.set(-ManualSpeed);
        m_talonFX_Minion.set(-ManualSpeed);
    }

    /**
     * Stops all Motors
     * 
     * @return void
     * @version 1.0
     */
    public void stop() {
        m_talonFX_Commander.set(0.0);
        m_talonFX_Minion.set(0.0);
    }

    /**
     * Returns the current position of the Launcher wheels
     * 
     * @return double - the reported encoder position
     * @version 1.0
     */
    public double getPositionCommander() {
        return m_talonFX_Commander.getPosition().getValueAsDouble();
    }

    public double getPositionMinion() {
        return m_talonFX_Minion.getPosition().getValueAsDouble();
    }

    /**
     * Gets the output current of the Launcher Motor
     * 
     * @version 1.0
     * @return Output amperage (double)
     */
    public double getCurrentCommander() {
        return m_talonFX_Commander.getSupplyCurrent(false).getValueAsDouble();
    }

    public double getCurrentMinon() {
        return m_talonFX_Minion.getSupplyCurrent(false).getValueAsDouble();
    }

    /**
     * Gets the current Launcher Motor Velocity (NOT RPM)
     * 
     * @version 1.0
     * @return Motor Velocity as a double
     */
    public double getVelocityCommander() {
        return m_talonFX_Commander.getVelocity().getValueAsDouble();
    }

    public double getVelocityMinion() {
        return m_talonFX_Minion.getVelocity().getValueAsDouble();
    }

}
