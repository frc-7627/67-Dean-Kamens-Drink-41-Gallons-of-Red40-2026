package frc.robot.subsystems.launcher;

import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MusicTone;
import com.ctre.phoenix6.hardware.TalonFX;
import static frc.robot.Constants.CanIDs.*;
import static frc.robot.Constants.LauncherConstants.*;

import frc.robot.subsystems.launcher.util.MotorsConfigurator;

class LauncherMotors {
    private final TalonFX commander = new TalonFX(LAUNCHER_COMMANDER_CAN_ID);
    private final TalonFX minion = new TalonFX(LAUNCHER_MINION_CAN_ID);
    private final MotorsConfigurator motorsConfigurator = new MotorsConfigurator(commander.getConfigurator(),
            minion.getConfigurator());
    private final Orchestra orchestra = new Orchestra();
    private boolean inMusicMode = false;

    /**
     * The launcher motors.
     */
    public LauncherMotors() {
        resetControl();
    }

    /**
     * Reset motor control to ensure motors are usable.
     * 
     * Sets the commander to target its own position, and the minion to follow the
     * commander.
     */
    private void resetControl() {
        commander.setControl(TARGET_DEFAULT_POSITION.withPosition(getCommanderPosition()));
        minion.setControl(new Follower(commander.getDeviceID(), null));
    }

    /**
     * Exit music mode.
     * 
     * Clears the orchestra and resets motor control.
     */
    public void exitMusicMode() {
        orchestra.stop();
        orchestra.clearInstruments();

        resetControl();

        inMusicMode = false;
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
        inMusicMode = true;

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
        inMusicMode = true;

        orchestra.addInstrument(commander);
        orchestra.addInstrument(minion);
        orchestra.loadMusic(filePath);
        orchestra.play();
    }

    public void setTangentialSpeed(double speed) {

    }

    /**
     * Set the commander motor's speed to the provided speed.
     * 
     * @param speed the provided speed.
     * @throws IllegalStateException if the motors are in music mode.
     */
    public void setCommanderSpeed(double speed) throws IllegalStateException {
        if (inMusicMode) {
            throw new IllegalStateException("Can only control motors when not in music mode!");
        }

        commander.set(speed);
    }

    /**
     * Set both motors' speed to the provided speed.
     * 
     * @param speed the provided speed.
     * @throws IllegalStateException if the motors are in music mode.
     */
    public void setBothSpeeds(double speed) throws IllegalStateException {
        if (inMusicMode) {
            throw new IllegalStateException("Can only control motors when not in music mode!");
        }

        commander.set(speed);
        minion.set(speed);
    }

    /**
     * Stop both motors.
     * 
     * @throws IllegalStateException if the motors are in music mode.
     */
    public void stopBoth() throws IllegalStateException {
        if (inMusicMode) {
            throw new IllegalStateException("Can only control motors when not in music mode!");
        }

        commander.set(0.0);
        minion.set(0.0);
    }

    /**
     * @return the position of the commander motor.
     */
    public double getCommanderPosition() {
        return commander.getPosition().getValueAsDouble();
    }

    /**
     * @return the supply current of the commander motor.
     */
    public double getCommanderCurrent() {
        return commander.getSupplyCurrent(false).getValueAsDouble();
    }

    /**
     * @return the velocity of the commander motor.
     */
    public double getCommanderVelocity() {
        return commander.getVelocity().getValueAsDouble();
    }

    /**
     * @return the position of the minion motor.
     */
    public double getMinionPosition() {
        return minion.getPosition().getValueAsDouble();
    }

    /**
     * @return the supply current of the minion motor.
     */
    public double getMinionCurrent() {
        return minion.getSupplyCurrent(false).getValueAsDouble();
    }

    /**
     * @return the velocity of the minion motor.
     */
    public double getMinionVelocity() {
        return minion.getVelocity().getValueAsDouble();
    }

    /**
     * @return the configurator for the motors.
     */
    public MotorsConfigurator getConfigurator() {
        return motorsConfigurator;
    }
}
