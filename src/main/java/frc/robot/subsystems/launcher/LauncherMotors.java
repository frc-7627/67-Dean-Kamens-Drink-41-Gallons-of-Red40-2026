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
    private final MotorsConfigurator motorsConfigurator =
            new MotorsConfigurator(commander.getConfigurator(), minion.getConfigurator());
    private final Orchestra orchestra = new Orchestra();
    private boolean inMusicMode = false;

    /**
     * The launcher motors.
     */
    public LauncherMotors() {
        resetControl();
    }

    private void resetControl() {
        commander.setControl(TARGET_DEFAULT_POSITION.withPosition(getCommanderPosition()));
        minion.setControl(new Follower(commander.getDeviceID(), null));
    }

    public void exitMusicMode() {
        orchestra.stop();
        orchestra.clearInstruments();

        resetControl();

        inMusicMode = false;
    }

    public void playNote(int freq) {
        inMusicMode = true;

        commander.setControl(new MusicTone(freq));
        minion.setControl(new MusicTone(freq));
    }

    public void playSongFromFile(String filePath) {
        inMusicMode = true;

        orchestra.addInstrument(commander);
        orchestra.addInstrument(minion);
        orchestra.loadMusic(filePath);
        orchestra.play();
    }

    public void setCommanderSpeed(double speed) throws IllegalStateException {
        if (inMusicMode) {
            throw new IllegalStateException("Can only control motors when not in music mode!");
        }

        commander.set(speed);
    }

    public void setBothSpeeds(double speed) throws IllegalStateException {
        if (inMusicMode) {
            throw new IllegalStateException("Can only control motors when not in music mode!");
        }

        commander.set(speed);
        minion.set(speed);
    }

    public void stopBoth() throws IllegalStateException {
        if (inMusicMode) {
            throw new IllegalStateException("Can only control motors when not in music mode!");
        }

        commander.set(0.0);
        minion.set(0.0);
    }

    public double getCommanderPosition() {
        return commander.getPosition().getValueAsDouble();
    }

    public double getCommanderCurrent() {
        return commander.getSupplyCurrent(false).getValueAsDouble();
    }

    public double getCommanderVelocity() {
        return commander.getVelocity().getValueAsDouble();
    }

    public double getMinionPosition() {
        return minion.getPosition().getValueAsDouble();
    }

    public double getMinionCurrent() {
        return minion.getSupplyCurrent(false).getValueAsDouble();
    }

    public double getMinionVelocity() {
        return minion.getVelocity().getValueAsDouble();
    }

    public MotorsConfigurator getConfigurator() {
        return motorsConfigurator;
    }
}
