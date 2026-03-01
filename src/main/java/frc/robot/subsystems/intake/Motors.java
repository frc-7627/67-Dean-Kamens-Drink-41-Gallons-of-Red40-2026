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

    private final TalonFX Intake = new TalonFX(INTAKE_MOTOR_CAN_ID);
    private final MotorsConfigurator motorsConfigurator = new MotorsConfigurator(Intake.getConfigurator());
    private final Orchestra orchestra = new Orchestra();
    private ControlMode mode = ControlMode.SIMPLE;

    /**
     * The launcher motors.
     */
    public Motors() {
        resetIntake();
    }

    /**
     * Reset motor control to ensure motors are usable.
     * 
     * Sets the Intake to target its own position, and the minion to follow the
     * Intake.
     */
    private void resetIntake() {
        Intake.setControl(TARGET_DEFAULT_POSITION.withPosition(getIntakePosition()));
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
            resetIntake();
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
            resetIntake();
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
        Intake.setControl(new MusicTone(freq));
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
        orchestra.addInstrument(Intake);
        orchestra.loadMusic(filePath);
        orchestra.play();
    }

    /**
     * Set the Intake motor's speed to the provided speed.
     * 
     * @param speed the provided speed.
     */
    public void setSpeed(double speed) {
        ensureSimple();
        Intake.set(speed);
    }

    public void setAngularSpeed(double velocityRotationsPerSec) {
       // ensureVelocity(); TODO: PUT THIS BACK BEFORE GIAN KILLS ME

        Intake.setControl(new VelocityVoltage(velocityRotationsPerSec));
    }
    
    /**
     * Stop Intake motor.
     */
    public void stop() {
        ensureSimple();
        Intake.set(0.0);
    }

    /**
     * @return the position of the Intake motor.
     */
    public Angle getIntakePosition() {
        return Rotations.of(Intake.getPosition().getValueAsDouble());
    }

    /**
     * @return the supply current of the Intake motor.
     */
    public Current getIntakeCurrent() {
        return Amps.of(Intake.getSupplyCurrent(false).getValueAsDouble());
    }

    /**
     * @return the velocity of the Intake motor.
     */
    public AngularVelocity getIntakeVelocity() {
        return RotationsPerSecond.of(Intake.getVelocity().getValueAsDouble());
    }

    public void applyCurrentLimit(double currentLimit) {
        motorsConfigurator.applyCurrentLimit(currentLimit);
    }

    public void applyRampUpPeriod(double rampUpPeriod) {
        motorsConfigurator.applyRampUpPeriod(rampUpPeriod);
    }
}
