// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Hertz;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.Milliseconds;
import static edu.wpi.first.units.Units.Second;
import java.io.File;
import java.util.function.Predicate;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.units.measure.Time;
import com.ctre.phoenix6.configs.AudioConfigs;
import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.RainbowAnimation;
import com.ctre.phoenix6.controls.SingleFadeAnimation;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.controls.StrobeAnimation;
import com.ctre.phoenix6.controls.TwinkleAnimation;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.gameinfo.GameInfoSupplier;
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
    public static final Mode currentMode = Mode.REAL; // TODO: CHECK THIS EVERY
    // TIME YOU DEPLOY OR
    // ELSE THE CODE WILL NOT THE CODE
    public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
    public static final Matter CHASSIS = // TODO: Figure out if CHASSIS is needed
            new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
    public static final double LOOP_TIME = 0.13; // s, 20ms + 110ms sprk max velocity lag
    public static final double MAX_SPEED = Units.feetToMeters(2);

    public static final Predicate<Double> CHECK_SIMPLE_MOTOR_SPEED = simpleMotorSpeed -> -1.0 <= simpleMotorSpeed
            && simpleMotorSpeed <= 1.0;

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
        public static final File SWERVE_CONFIG_FILE = new File(Filesystem.getDeployDirectory(), "swerve");

        public static final Pose2d RED_ALLIANCE_INITIAL_POSE = new Pose2d(
                new Translation2d(Meter.of(16), Meter.of(4)), Rotation2d.fromDegrees(180));
        public static final Pose2d BLUE_ALLIANCE_INITIAL_POSE = new Pose2d(new Translation2d(Meter.of(1), Meter.of(4)),
                Rotation2d.fromDegrees(0));

        // These are for checking whether the robot has settled to an orientation.
        public static final Angle ANGULAR_EPSILON = Degrees.of(1.);
        public static final AngularVelocity ANGULAR_VELOCITY_EPSILON = Degrees.of(1.).per(Second);
        public static final Time CONVERGENCE_PERIOD = Milliseconds.of(20);

        // Hold time on motor brakes when disabled
        public static final double WHEEL_LOCK_TIME = 10; // seconds

    }

    public static final class VisionConstants {
        public static final String LEFT_CAMERA_NAME = "PC_Camera SIG";
        public static final String RIGHT_CAMERA_NAME = "PC_Camera_MA";

        public static final Transform3d LEFT_CAMERA_TRANSFORM = new Transform3d(
                new Translation3d(Units.inchesToMeters(8),
                        Units.inchesToMeters(1), Units.inchesToMeters(8)),
                new Rotation3d(0, Units.degreesToRadians(30), 25));
        public static final Transform3d RIGHT_CAMERA_TRANSFORM = new Transform3d(
                new Translation3d(Units.inchesToMeters(5.840),
                        Units.inchesToMeters(10.776),
                        Units.inchesToMeters(7.776)),
                new Rotation3d(0, Units.degreesToRadians(30), 70));

        public static final int MAX_CONNECTION_RETRIES = 10;

        public static final double DEFAULT_SINGLE_TAG_STD_DEV = 0.5;
        public static final double DEFAULT_MULTI_TAG_STD_DEV = 1.0;

        // TODO: set field to 2026.
        public static final AprilTagFieldLayout FIELD_LAYOUT = AprilTagFieldLayout
                .loadField(AprilTagFields.k2025ReefscapeWelded);

        public static final String VISION_ESTIMATION_OBJECT_NAME = "VisionEstimation";

        public static final String TRACKED_TARGETS_OBJECT_NAME = "tracked targets";

        public static final Translation2d HUB_LOCATION = new Translation2d(Inches.of(182.56), Inches.of(0));
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
        public static final int LAST_ATTACHED_LED_NUM = Math.min(LAST_ONBOARD_LED_NUM + ATTACHED_LED_COUNT,
                FIRST_ATTACHED_LED_NUM);

        private static final int LAST_LED_NUM = LAST_ATTACHED_LED_NUM;

        public static final SolidColor CONTROL_ALL_LEDS = new SolidColor(0, Constants.LEDConstants.LAST_LED_NUM);

        public static final StrobeAnimation STROBE_ANIMATION = new StrobeAnimation(0, LAST_ATTACHED_LED_NUM);

        public static final RainbowAnimation RAINBOW_ANIMATION = new RainbowAnimation(FIRST_ATTACHED_LED_NUM,
                LAST_ATTACHED_LED_NUM);

        public static final TwinkleAnimation TWINKLE_ANIMATION = new TwinkleAnimation(FIRST_ATTACHED_LED_NUM,
                LAST_ATTACHED_LED_NUM);

        public static final SingleFadeAnimation FADE_ANIMATION = new SingleFadeAnimation(FIRST_ATTACHED_LED_NUM,
                LAST_ATTACHED_LED_NUM);

        public static final Frequency STROBE_FREQUENCY = Frequency.ofRelativeUnits(1.0, Hertz);
        public static final Frequency TWINK_FREQUENCY = Frequency.ofRelativeUnits(1.0, Hertz);
        public static final Frequency FADE_FREQUENCY = Frequency.ofRelativeUnits(1.0, Hertz);
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
        public static final GameInfoSupplier.Phase START_PHASE = GameInfoSupplier.Phase.AUTO;
        public static final Alliance DEFAULT_ALLIANCE = Alliance.Red;
    }

    public static class CanIDs {
        public static final int PROTOTYPE_MOTOR_CAN_ID = 14;

        public static final int CANDLE_CAN_ID = 15;

        public static final int LAUNCHER_COMMANDER_CAN_ID = 16;
        public static final int LAUNCHER_MINION_CAN_ID = 17;

        // TODO: configure can_id for feeder
        public static final int FEEDER_CAN_ID = -1;
    }

    public static class IntakeConstants {
        public static final int AMP_LIMIT = 150;

        // in range [-1.0, 1.0]
        public static final double DEFAULT_LOAD_SPEED = 0.8;
    }

    public static class LauncherConstants {
        public static final MotionMagicVoltage TARGET_DEFAULT_POSITION = new MotionMagicVoltage(0);

        public static final double DEFAULT_SHOOT_SPEED = 0.7;
        public static final double DEFAULT_ACTIVE_IDLE_SPEED = 0.5;
        public static final double DEFAULT_INACTIVE_IDLE_SPEED = 0.3;
        public static final double DEFAULT_MANUAL_SPEED = 0.3;

        public static final double DEFAULT_RAMP_UP_PERIOD = 0.5;

        public static final double DEFAULT_CURRENT_LIMIT = 40;

        public static final CurrentLimitsConfigs DEFAULT_CURRENT_LIMITS_CONFIGS = new CurrentLimitsConfigs()
                .withStatorCurrentLimitEnable(true)
                .withStatorCurrentLimit(DEFAULT_CURRENT_LIMIT);

        public static final MotorOutputConfigs DEFAULT_MOTOR_OUTPUT_CONFIGS = new MotorOutputConfigs()
                .withPeakForwardDutyCycle(DEFAULT_SHOOT_SPEED)
                .withPeakReverseDutyCycle(-DEFAULT_SHOOT_SPEED)
                .withNeutralMode(NeutralModeValue.Coast);

        public static final OpenLoopRampsConfigs DEFAULT_OPEN_LOOP_RAMPS_CONFIGS = new OpenLoopRampsConfigs()
                .withDutyCycleOpenLoopRampPeriod(DEFAULT_RAMP_UP_PERIOD);

        public static final ClosedLoopRampsConfigs DEFAULT_CLOSED_LOOP_RAMPS_CONFIGS = new ClosedLoopRampsConfigs()
                .withDutyCycleClosedLoopRampPeriod(DEFAULT_RAMP_UP_PERIOD);

        public static final AudioConfigs AUDIO_CONFIGS = new AudioConfigs().withBeepOnBoot(false)
                .withBeepOnConfig(false).withAllowMusicDurDisable(true);

        public static final int HORN_FREQ = 440;
    }

    public static class FeederConstants {
        // TODO: find values for feeder
        public static final double DEFAULT_FEED_SPEED = -1;

        public static final double DEFAULT_CURRENT_LIMIT = 40;

        public static final CurrentLimitsConfigs DEFAULT_CURRENT_LIMITS_CONFIGS = new CurrentLimitsConfigs()
                .withStatorCurrentLimitEnable(true)
                .withStatorCurrentLimit(DEFAULT_CURRENT_LIMIT);

        public static final MotorOutputConfigs DEFAULT_MOTOR_OUTPUT_CONFIGS = new MotorOutputConfigs()
                .withPeakForwardDutyCycle(DEFAULT_FEED_SPEED)
                .withPeakReverseDutyCycle(-DEFAULT_FEED_SPEED)
                .withNeutralMode(NeutralModeValue.Coast);
    }

    public static class Directories {
        public static final String DEPLOY_DIRECTORY = Filesystem.getDeployDirectory().getAbsolutePath();

        public static final String SONGS_DIRECTORY = String.format("%s/%s", DEPLOY_DIRECTORY, "songs");
    }

    public static class StartupConstants {
        public static final String STARTUP_BANNER_FILE_PATH = String.format("%s/startup-banner.txt",
                Directories.DEPLOY_DIRECTORY);
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
