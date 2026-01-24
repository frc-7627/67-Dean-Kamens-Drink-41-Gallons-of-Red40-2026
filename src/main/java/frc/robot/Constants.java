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
import com.ctre.phoenix6.controls.RainbowAnimation;
import com.ctre.phoenix6.controls.SolidColor;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.GameInfo;
import swervelib.math.Matter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Logging mode for advantage kit logs. Switch between, REAL , SIM , and REPLAY
    // based on whatever
    // mode you need
    // public static final Mode currentMode = Mode.REAL; // TODO: CHECK THIS EVERY
    // TIME YOU DEPLOY OR
    // ELSE THE CODE WILL NOT THE CODE
    // TODO: Fix this at some point ^^^^
    public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
    public static final Matter CHASSIS = // TODO: Figure out if CHASSIS is needed
            new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
    public static final double LOOP_TIME = 0.13; // s, 20ms + 110ms sprk max velocity lag
    public static final double MAX_SPEED = Units.feetToMeters(2);
    // Maximum speed of the robot in meters per second, used to limit acceleration.

    // public static final class AutonConstants
    // {
    //
    // public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0,
    //
    // 0);
    // public static final PIDConstants ANGLE_PID = new PIDConstants(0.4, 0, 0.01);
    // }

    public static final class DrivebaseConstants {

        public static double x_offset = Units.inchesToMeters(16.6220472441);

        public static double y_offset = 0.0;
        public static double y_offset_left = Units.inchesToMeters(-4.268); // add inches to center
        // //TODO: these ^^^ will need to be retuned
        public static double y_offset_right = Units.inchesToMeters(8.268); // add inches away from center

        // Hold time on motor brakes when disabled
        public static final double WHEEL_LOCK_TIME = 10; // seconds

    }

    public static class OperatorConstants {

        // Joystick Deadband
        public static final double DEADBAND = 0.1;
        public static final double LEFT_Y_DEADBAND = 0.1;
        public static final double RIGHT_X_DEADBAND = 0.1;
        public static final double TURN_CONSTANT = 6;
    }

    public static class LEDConstants {
        private static final int ONBOARD_LED_COUNT = 8;
        private static final int LAST_ONBOARD_LED_NUM = ONBOARD_LED_COUNT - 1;

        public static final int ATTACHED_LED_COUNT = 0;

        public static final int FIRST_ATTACHED_LED_NUM = ONBOARD_LED_COUNT;
        public static final int LAST_ATTACHED_LED_NUM = LAST_ONBOARD_LED_NUM + ATTACHED_LED_COUNT;

        private static final int LAST_LED_NUM = LAST_ATTACHED_LED_NUM;

        public static final SolidColor CONTROL_ALL_LEDS = new SolidColor(0, Constants.LEDConstants.LAST_LED_NUM);

        public static final RainbowAnimation RAINBOW_ANIMATION = new RainbowAnimation(FIRST_ATTACHED_LED_NUM,
                LAST_ATTACHED_LED_NUM);
    }

    public static class IndicatorConstants {
        public static class ColorArrays {
            /**
             * All default colors.
             * 
             * Indices are in the following order: alliance, game phase, color channel.
             */
            public static final int[][][] DEFAULT_COLOR_ARRAYS = {
                    /* Red alliance. */ { /* Auto */ { 255, 0, 0 }, /* Transition */ { 255, 0, 0 },
                            /* Teleop 1 */ { 255, 0, 0 }, /* Teleop 2 */ { 255, 0, 0 },
                            /* Teleop 3 */ { 255, 0, 0 }, /* Teleop 4 */{ 255, 0, 0 },
                            /* Endgame */ { 255, 0, 0 }, },
                    /* Blue alliance. */ { /* Auto */ { 0, 0, 255 }, /* Transition */ { 0, 0, 255 },
                            /* Teleop 1 */ { 0, 0, 255 }, /* Teleop 2 */ { 0, 0, 255 },
                            /* Teleop 3 */ { 0, 0, 255 }, /* Teleop 4 */{ 0, 0, 255 },
                            /* Endgame */ { 0, 0, 255 }, }, };

            public static final int[] COMPLETION_COLOR_ARRAY = { 137, 162, 3 };

            public static final int[] INTERRUPTION_COLOR_ARRAY = { 97, 64, 81 };

            public static final int[] PROGRESS_BAR_COLOR_ARRAY = { 255, 255, 255 };
        }

    }

    public static class SixtySeven {
        public static final double SixitySeven = 67.67;
    }

    public static class GameInfoConstants {
        public static final GameInfo.Phase START_PHASE = GameInfo.Phase.AUTO;
        public static final Alliance DEFAULT_ALLIANCE = Alliance.Red;
    }

    public static class CanIDs {
        public static final int PROTOTYPE_MOTOR_CAN_ID = 14;

        public static final int CANDLE_CAN_ID = 15;
    }

    public static class IntakeConstants {
        public static final int AMP_LIMIT = 150;

        // in range [-1.0, 1.0]
        public static final double DEFAULT_LOAD_SPEED = 0.8;
    }
}
