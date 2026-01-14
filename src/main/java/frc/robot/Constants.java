// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.junction.LogFileUtil;

import com.pathplanner.lib.config.PIDConstants;

import edu.wpi.first.math.util.Units;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 * 
 * @version 1.0
 */
public final class Constants {

  // Logging mode for advantage kit logs. Switch between, REAL , SIM , and REPLAY based on whatever
  // mode you need
  public static final Mode currentMode = Mode.REAL; // TODO: CHECK THIS EVERY TIME YOU DEPLOY OR
                                                    // ELSE THE CODE WILL NOT THE CODE

  // Enable this to allow for coach controller to cause simulated failures in subsystems
  public static final boolean verbose_shuffleboard_logging = true; // debug

  // public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
  // public static final Matter CHASSIS = new Matter(new Translation3d(0, 0,
  // Units.inchesToMeters(28)), ROBOT_MASS);
  // public static final double LOOP_TIME = 0.13; //s, 20ms + 110ms sprk max velocity lag
  public static final double MAX_SPEED = Units.feetToMeters(13);
  // Maximum speed of the robot in meters per second, used to limit acceleration.



  // public static final class AutonConstants
  // {
  //
  // public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
  // public static final PIDConstants ANGLE_PID = new PIDConstants(0.4, 0, 0.01);
  // }

  public static final class DrivebaseConstants {

    public static double x_offset = Units.inchesToMeters(16.6220472441);
    public static double y_offset = 0.0; // L = 0.2 R = 0.6
    public static double y_offset_left = Units.inchesToMeters(-4.268); // add inches to center
                                                                       // //TODO: When I add those
                                                                       // pi cams these will need to
                                                                       // be retuned
    public static double y_offset_right = Units.inchesToMeters(8.268); // add inches away from
                                                                       // center

    // public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
    // public static final PIDConstants ANGLE_PID = new PIDConstants(0.4, 0, 0.01);


    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static final class BluetoothConstants {
    public static final int NUM_LEDS = 13;

    public static final String DEFAULT_COLOR = "orange";
    public static final String COMPLETION_COLOR = "vomitGreen";
    public static final String INTERRUPTION_COLOR = "eggPlant";
  }

  public static class OperatorConstants {

    // Joystick Deadband
    public static final double DEADBAND = 0.1;
    // public static final double LEFT_Y_DEADBAND = 0.1;
    // public static final double RIGHT_X_DEADBAND = 0.1;
    // public static final double TURN_CONSTANT = 6;
  }
  public enum Mode {
    // Running on a real robot

    REAL,

    // Running in the sim

    SIM,

    // Replaying from a log file

    REPLAY
  }
}


// test from laptop
