package frc.robot.subsystems.launcher;

import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MusicTone;
import com.ctre.phoenix6.hardware.TalonFX;
import static frc.robot.Constants.CanIDs.*;
import static frc.robot.Constants.LauncherConstants.*;
import static frc.robot.Constants.Directories.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.launcher.util.MotorsConfigurator;

public class LauncherMotors extends SubsystemBase {
    public static enum Song {
        ;

        private final String filePath;

        Song(String simpleFileName) {
            this.filePath = String.format("%s/%s.chrp", SONGS_DIRECTORY, simpleFileName);
        }

        public String getFilePath() {
            return filePath;
        }
    }

    private final TalonFX commander = new TalonFX(LAUNCHER_COMMANDER_CAN_ID);
    private final TalonFX minion = new TalonFX(LAUNCHER_MINION_CAN_ID);
    private final MotorsConfigurator motorsConfigurator =
            new MotorsConfigurator(commander.getConfigurator(), minion.getConfigurator());
    private final Orchestra orchestra = new Orchestra();

    public LauncherMotors() {
        reset();
    }

    public void reset() {
        orchestra.stop();
        orchestra.clearInstruments();

        commander.setControl(TARGET_DEFAULT_POSITION.withPosition(getCommanderPosition()));
        minion.setControl(new Follower(commander.getDeviceID(), null));
    }

    public void playNote(int freq) {
        commander.setControl(new MusicTone(freq));
        minion.setControl(new MusicTone(freq));
    }

    public void playSong(Song song) {
        orchestra.addInstrument(commander);
        orchestra.addInstrument(minion);
        orchestra.loadMusic(song.getFilePath());
        orchestra.play();
    }

    public void setCommander(double speed) {
        commander.set(speed);
    }

    public void setBoth(double speed) {
        commander.set(speed);
        minion.set(speed);
    }

    public void stop() {
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
