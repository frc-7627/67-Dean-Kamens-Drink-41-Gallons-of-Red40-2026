package frc.robot.subsystems.launcher;

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
import static frc.robot.Constants.LauncherConstants.*;

final class Motors {
    private static enum ControlMode {
        SIMPLE,
        MUSIC,
        VELOCITY;

        boolean isMusic() {
            return equals(MUSIC);
        }
    }

    private static final VelocityVoltage VELOCITY_CONTROL = new VelocityVoltage(0.0);

    private final TalonFX commander = new TalonFX(LAUNCHER_COMMANDER_CAN_ID);
    private final TalonFX minion = new TalonFX(LAUNCHER_MINION_CAN_ID);
    private final MotorsConfigurator motorsConfigurator = new MotorsConfigurator(commander.getConfigurator(),
            minion.getConfigurator());
    private final Orchestra orchestra = new Orchestra();
    private ControlMode mode = ControlMode.SIMPLE;

    /**
     * The launcher motors.
     */
    public Motors() {
        resetCommander();
    }

    private void resetMinion() {
        minion.setControl(new Follower(commander.getDeviceID(), MotorAlignmentValue.Aligned));
    }

    /**
     * Reset motor control to ensure motors are usable.
     * 
     * Sets the commander to target its own position, and the minion to follow the
     * commander.
     */
    private void resetCommander() {
        commander.setControl(TARGET_DEFAULT_POSITION.withPosition(getCommanderPosition()));
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

        resetCommander();
        resetMinion();

        mode = ControlMode.SIMPLE;
    }

    private void ensureMusic() {
        mode = ControlMode.MUSIC;
    }

    private void ensureVelocity() {
        if (mode.isMusic()) {
            exitMusic();
        }

        resetCommander();
        resetMinion();

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

        commander.setControl(new MusicTone(freq));
        minion.setControl(new MusicTone(freq));
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
        orchestra.addInstrument(commander);
        orchestra.addInstrument(minion);
        orchestra.loadMusic(filePath);
        orchestra.play();
    }

    /**
     * Set the commander motor's speed to the provided speed.
     * 
     * @param speed the provided speed.
     */
    public void setSpeed(double speed) {
        ensureSimple();

        commander.set(speed);
    }

    public void setAngularSpeed(double velocityRotationsPerSec) {
        ensureVelocity();

        commander.setControl(VELOCITY_CONTROL.withVelocity(velocityRotationsPerSec));
    }

    /**
     * 
     */
    
    /**
     * Stop both motors.
     */
    public void stop() {
        ensureSimple();

        commander.set(0.0);
    }

    /**
     * @return the position of the commander motor.
     */
    public Angle getCommanderPosition() {
        return Rotations.of(commander.getPosition().getValueAsDouble());
    }

    /**
     * @return the supply current of the commander motor.
     */
    public Current getCommanderCurrent() {
        return Amps.of(commander.getSupplyCurrent(false).getValueAsDouble());
    }

    /**
     * @return the velocity of the commander motor.
     */
    public AngularVelocity getCommanderVelocity() {
        return RotationsPerSecond.of(commander.getVelocity().getValueAsDouble());
    }

    /**
     * @return the position of the minion motor.
     */
    public Angle getMinionPosition() {
        return Rotations.of(minion.getPosition().getValueAsDouble());
    }

    /**
     * @return the supply current of the minion motor.
     */
    public Current getMinionCurrent() {
        return Amps.of(minion.getSupplyCurrent(false).getValueAsDouble());
    }

    /**
     * @return the velocity of the minion motor.
     */
    public AngularVelocity getMinionVelocity() {
        return RotationsPerSecond.of(minion.getVelocity().getValueAsDouble());
    }

    public void applyCurrentLimit(double currentLimit) {
        motorsConfigurator.applyCurrentLimit(currentLimit);
    }

    public void applyRampUpPeriod(double rampUpPeriod) {
        motorsConfigurator.applyRampUpPeriod(rampUpPeriod);
    }
}
