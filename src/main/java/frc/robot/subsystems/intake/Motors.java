package frc.robot.subsystems.intake;

import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MusicTone;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static frc.robot.Constants.CanIDs.*;
import static frc.robot.Constants.IntakeConstants.*;

final class Motors {
    private static enum ControlMode {
        SIMPLE,
        MUSIC,
        VELOCITY;

        boolean isMusic() {
            return equals(MUSIC);
        }

        boolean isSimple() {
            return equals(SIMPLE);
        }
    }

    private final TalonFX Kraken = new TalonFX(INTAKE_MOTOR_CAN_ID);
    private final MotorsConfigurator motorsConfigurator = new MotorsConfigurator(Kraken.getConfigurator());
    private final Orchestra orchestra = new Orchestra();
    private ControlMode mode = ControlMode.SIMPLE;

    /**
     * The launcher motors.
     */
    public Motors() {
        resetKraken();
    }

    /**
     * Reset motor control to ensure motors are usable.
     * 
     * Sets the Kraken to target its own position, and the minion to follow the
     * Kraken.
     */
    private void resetKraken() {
        Kraken.setControl(TARGET_DEFAULT_POSITION.withPosition(getKrakenPosition()));
    }

    /**
     * Exit music mode.
     * 
     * Clears the orchestra and resets motor control.
     */
    private void exitMusic() {
        orchestra.stop();
        orchestra.clearInstruments();
    }

    private void ensureSimple() {
        if (mode.isMusic()) {
            exitMusic();
        }

        if (!mode.isSimple()) {
            resetKraken();
        }

        mode = ControlMode.SIMPLE;
    }

    private void ensureMusic() {
        mode = ControlMode.MUSIC;
    }

    private void ensureVelocity() {
        if (mode.isMusic()) {
            exitMusic();
        }

        if (!mode.isSimple()) {
            resetKraken();
        }

        mode = ControlMode.VELOCITY;
    }

    /**
     * Play a note of the provided frequency.
     * 
     * Enters music mode and plays the note on each motor.
     * 
     * @param freq the provided frequency, in hertz.
     * @apiNote Enters music mode.
     */
    public void playNote(int freq) {
        ensureMusic();
        Kraken.setControl(new MusicTone(freq));
    }

    /**
     * Play a song from the provided file path.
     * 
     * Enters music mode, adds the motors to the orchestra, loads the song file, and
     * plays the song.
     * 
     * @param filePath the provided file path. This should be a {@code *.chrp} file.
     * @apiNote Enters music mode.
     */
    public void playSongFromFile(String filePath) {
        orchestra.addInstrument(Kraken);
        orchestra.loadMusic(filePath);
        orchestra.play();
    }

    /**
     * Set the Kraken motor's speed to the provided speed.
     * 
     * @param speed the provided speed.
     */
    public void setSpeed(double speed) {
        ensureSimple();
        Kraken.set(speed);
    }

    public void setAngularSpeed(double velocityRotationsPerSec) {
       // ensureVelocity(); TODO: PUT THIS BACK BEFORE GIAN KILLS ME

        Kraken.setControl(new VelocityVoltage(velocityRotationsPerSec));
    }
    
    /**
     * Stop Kraken motor.
     */
    public void stop() {
        ensureSimple();
        Kraken.set(0.0);
    }

    /**
     * @return the position of the Kraken motor.
     */
    public Angle getKrakenPosition() {
        return Rotations.of(Kraken.getPosition().getValueAsDouble());
    }

    /**
     * @return the supply current of the Kraken motor.
     */
    public Current getKrakenCurrent() {
        return Amps.of(Kraken.getSupplyCurrent(false).getValueAsDouble());
    }

    /**
     * @return the velocity of the Kraken motor.
     */
    public AngularVelocity getKrakenVelocity() {
        return RotationsPerSecond.of(Kraken.getVelocity().getValueAsDouble());
    }

    public void applyCurrentLimit(double currentLimit) {
        motorsConfigurator.applyCurrentLimit(currentLimit);
    }

    public void applyRampUpPeriod(double rampUpPeriod) {
        motorsConfigurator.applyRampUpPeriod(rampUpPeriod);
    }
}
