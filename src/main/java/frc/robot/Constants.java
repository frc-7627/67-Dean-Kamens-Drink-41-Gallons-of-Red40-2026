// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Feet;
import static edu.wpi.first.units.Units.Hertz;
import static edu.wpi.first.units.Units.Inch;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.Milliseconds;
import static edu.wpi.first.units.Units.Second;
import java.io.File;
import java.util.Arrays;
import java.util.function.DoublePredicate;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.units.measure.Time;
import com.ctre.phoenix6.configs.AudioConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
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
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.controllable.drivebase.InputMode;
import frc.robot.subsystems.misc.indication.AllianceDefaultColors;
import frc.robot.subsystems.shared.gameinfo.GameInfoSupplier;
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
    public static final Mode currentMode = Mode.REAL; // TODO: CHECK THIS EVERY TIME YOU DEPLOY OR ELSE THE CODE WILL
                                                      // NOT THE CODE
    public static final double ROBOT_MASS = (125 - 20.3) * 0.453592; // 32lbs * kg per pound
    public static final Matter CHASSIS = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
    public static final double LOOP_TIME = 0.13; // s, 20ms + 110ms spark max velocity lag
    public static final double MAX_SPEED = Units.feetToMeters(16);

    //PATHPLANNER MOI IS PRETTY MUCH GUESSTIMATION

    public static final DoublePredicate CHECK_DUTY_CYCLE = simpleMotorSpeed -> -1.0 <= simpleMotorSpeed
            && simpleMotorSpeed <= 1.0;

    public static final Frequency MOTOR_CONFIGURE_FREQUENCY = Hertz.of(5);

    // Maximum speed of the robot in meters per second, used to limit acceleration.

    public static final class DrivebaseConstants {
        public static final InputMode MODE = InputMode.ROTATE;

        public static final File SWERVE_CONFIG_FILE = new File(Filesystem.getDeployDirectory(), "swerve");

        public static final double BLUE_ALLIANCE_ZONE_X = 4.5;
        public static final double RED_ALLIANCE_ZONE_X = 12;

        public static final double FIELD_MIDLINE_Y = 4;

        public static final Pose2d RED_ALLIANCE_INITIAL_POSE = new Pose2d(
                new Translation2d(Meter.of(16), Meter.of(4)), Rotation2d.fromDegrees(180));
        public static final Pose2d BLUE_ALLIANCE_INITIAL_POSE = new Pose2d(new Translation2d(Meter.of(1), Meter.of(4)),
                Rotation2d.fromDegrees(0));

        public static final Translation2d BLUE_LEFT_FERRY_TARGET_POSITION = new Translation2d(
                Meter.of(2), Meter.of(6)
        );

        public static final Translation2d BLUE_RIGHT_FERRY_TARGET_POSITION = new Translation2d(
                Meter.of(2), Meter.of(2)
        );

        public static final Translation2d RED_LEFT_FERRY_TARGET_POSITION = new Translation2d(
                Meter.of(14), Meter.of(6)
        );

        public static final Translation2d RED_RIGHT_FERRY_TARGET_POSITION = new Translation2d(
                Meter.of(14), Meter.of(2)
        );

        // These are for checking whether the robot has settled to an orientation.
        public static final Angle ANGULAR_EPSILON = Degrees.of(1.);
        public static final AngularVelocity ANGULAR_VELOCITY_EPSILON = Degrees.of(1.).per(Second);
        public static final Time CONVERGENCE_PERIOD = Milliseconds.of(20);

        // Hold time on motor brakes when disabled
        public static final double WHEEL_LOCK_TIME = 10; // seconds

    }

    public static final class VisionConstants {
        public static final boolean VISION_ENABLED = true;

        public static final String LEFT_CAMERA_NAME = "PC_Camera SIG";
        public static final String RIGHT_CAMERA_NAME = "PC_Camera_MA";

        public static final Transform3d LEFT_CAMERA_TRANSFORM = new Transform3d(
                new Translation3d(
                        Units.inchesToMeters(-6.825), // x
                        Units.inchesToMeters(4.798), // y
                        Units.inchesToMeters(18.354)), // z
                new Rotation3d(
                        Units.degreesToRadians(0), //roll
                        Units.degreesToRadians(10.355), //pitch
                        Units.degreesToRadians(-5))); //yaw
        public static final Transform3d RIGHT_CAMERA_TRANSFORM = new Transform3d(
                new Translation3d(
                        Units.inchesToMeters(-6.825), // x
                        Units.inchesToMeters(-4.798), // y
                        Units.inchesToMeters(18.354)), // z
                new Rotation3d(
                        Units.degreesToRadians(0), //roll
                        Units.degreesToRadians(10.355), //pitch
                        Units.degreesToRadians(5))); //yaw

        public static final int MAX_CONNECTION_RETRIES = 10;

        public static final double DEFAULT_SINGLE_TAG_STD_DEV = 0.5;
        public static final double DEFAULT_MULTI_TAG_STD_DEV = 1.0;

        public static final AprilTagFieldLayout FIELD_LAYOUT = AprilTagFieldLayout
                .loadField(AprilTagFields.k2026RebuiltWelded);

        public static final String VISION_ESTIMATION_OBJECT_NAME = "VisionEstimation";

        public static final String TRACKED_TARGETS_OBJECT_NAME = "tracked targets";

        public static final Translation2d BLUE_HUB_LOCATION = new Translation2d(4.6256, 4.0345);
        public static final Translation2d RED_HUB_LOCATION = new Translation2d(11.9154, 4.0345);
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

        public static final int ATTACHED_LED_COUNT = 14;

        public static final int FIRST_ATTACHED_LED_NUM = ONBOARD_LED_COUNT;
        public static final int LAST_ATTACHED_LED_NUM = Math.max(LAST_ONBOARD_LED_NUM + ATTACHED_LED_COUNT,
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
            public static final AllianceDefaultColors BLUE_DEFAULT_COLORS = new AllianceDefaultColors(
                // Auto #ff0000
                new int[]{ 0, 0, 255 },
                // Transition #ff0000
                new int[]{ 0, 0, 255 },
                // Teleop Inactive #ff0000
                new int[]{ 0, 0, 255 },
                // Teleop Active #ff0000
                new int[]{ 0, 0, 255 },
                // Endgame #ff0000
                new int[]{ 0, 0, 255 }
            );

            public static final AllianceDefaultColors RED_DEFAULT_COLORS = new AllianceDefaultColors(
                // Auto #0000ff
                new int[]{ 255, 0, 0 },
                // Transition #0000ff
                new int[]{ 255, 0, 0 },
                // Teleop Inactive #0000ff
                new int[]{ 255, 0, 0 },
                // Teleop Active #0000ff
                new int[]{ 255, 0, 0 },
                // Endgame #0000ff
                new int[]{ 255, 0, 0 }
            );

            // #89a203
            public static final int[] COMPLETION_COLOR_ARRAY = { 137, 162, 3 }; 

            // #614051
            public static final int[] INTERRUPTION_COLOR_ARRAY = { 97, 64, 81 };

            // #fcc603
            public static final int[] PROGRESS_BAR_COLOR_ARRAY = { 252, 198, 3 };

            // #03fcf8
            public static final int[] RAMPING_COLOR_ARRAY = { 3, 252, 248 };

            // #18fc03
            public static final int[] SHOOTING_COLOR_ARRAY = { 24, 252, 3 };

            // #fc03fc
            public static final int[] RE_RAMPING_COLOR_ARRAY = { 252, 3, 252 };
        }

    }

    public static class SixtySeven {
        public static final double SIXTY_SEVEN = 67.67;
    }

    public static class GameInfoConstants {
        public static final GameInfoSupplier.Phase START_PHASE = GameInfoSupplier.Phase.AUTO;
        public static final Alliance DEFAULT_ALLIANCE = Alliance.Blue;
    }

    public static class CanIDs {
        public static final int SWIVEL_MOTOR_CAN_ID = 14;
        public static final int INTAKE_MOTOR_CAN_ID = 15;

        public static final int CANDLE_CAN_ID = 16;

        public static final int LAUNCHER_COMMANDER_CAN_ID = 16;
        public static final int LAUNCHER_MINION_CAN_ID = 17;

        public static final int FEEDER_CAN_ID = 18;

        public static final int AGITATOR_MOTOR_CAN_ID = 19;

        public static final int CLIMBER_CAN_ID = 20;

    }

    public static class AgitatorConstants {
        public static final int AMP_LIMIT = 150; // that's really high so im not gonna change it

        public static final double DEFAULT_DUTY_CYCLE = -0.8;
        public static final double DEFAULT_MANUAL_DUTY_CYCLE = -0.2;
    }

    public static class IntakeConstants {
        public static final MotionMagicVoltage TARGET_DEFAULT_POSITION = new MotionMagicVoltage(0);

        public static final int AMP_LIMIT = 80;

        public static final double DEFAULT_MANUAL_DUTY_CYCLE = 0.8;

        // in range [-1.0, 1.0]
        public static final double DEFAULT_INTAKE_DUTY_CYCLE = -0.8;

        public static final AudioConfigs AUDIO_CONFIGS = new AudioConfigs().withBeepOnBoot(false)
                .withBeepOnConfig(false).withAllowMusicDurDisable(true);
    }

    public static class SwivelConstants {
        public static final int AMP_LIMIT = 30;

        public static final double DEFAULT_MANUAL_DUTY_CYCLE = 0.6;

        public static final double DEFAULT_FOLD_DUTY_CYCLE = -0.1;

        public static final double IN_POSITION_DEGREES = -30; //INTAKE ROCK UP

        public static final double OUT_POSITION_DEGREES = -79 + (-79 * 0.02);

        public static final double SWIVEL_TO_MOTOR_GEAR_RATIO = 40;
    }

    public static class LauncherConstants {
        public static final MotionMagicVoltage TARGET_DEFAULT_POSITION = new MotionMagicVoltage(0);

        public static final double FLYWHEEL_RADIUS_FEET = Feet.convertFrom(2, Inch);; // inches

        // FEET PER SEC
        // Math says (not accounting for energy loss) that from trench to hub is
        // 1573.85107 RPS z
        // or 83.49530895144358 FPS
        public static final double DEFAULT_SHOOT_FPS = 41; //39 for trench, 44.5 for outpost, 34.3 for minimum, 
        // 44 for midfield ferry, 55 for opposing bump ferry
        public static final double DEFAULT_ACTIVE_IDLE_FPS = DEFAULT_SHOOT_FPS /1.3333;
        public static final double DEFAULT_INACTIVE_IDLE_FPS = DEFAULT_SHOOT_FPS /1.3333; 

        public static final double DEFAULT_RAMP_UP_PERIOD = 0.1;

        public static final double DEFAULT_CURRENT_LIMIT = 70;

        public static final MotorOutputConfigs MOTOR_OUTPUT_CONFIGS = new MotorOutputConfigs()
                .withNeutralMode(NeutralModeValue.Coast);

        public static final AudioConfigs AUDIO_CONFIGS = new AudioConfigs().withBeepOnBoot(false)
                .withBeepOnConfig(false).withAllowMusicDurDisable(true);

        public static final double DEFAULT_SLOT0_P = 0.5;
        public static final double DEFAULT_SLOT0_I = 0.0;
        public static final double DEFAULT_SLOT0_D = 0.0;
        public static final double DEFAULT_SLOT0_V = 0.111;
        public static final double DEFAULT_SLOT0_S = 0.25; // .25 volts to overcome static friction (DONT CHANGE)

        public static final int HORN_FREQ = 440;

        private static final double[][] CLOSE_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_ARRAY = {
            {6.2, 36.3}, // min //35.3
            {11, 40}, // trench/depot //39
            {17, 46.5} // max //45.5
        };

        private static final double[][] FAR_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_ARRAY = {
            {27, 44},
            {37, 55}
        };

        public static final InterpolatingDoubleTreeMap CLOSE_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_MAP = 
        new InterpolatingDoubleTreeMap();

        static {
            Arrays.stream(CLOSE_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_ARRAY).forEach(entry -> {
                final double key = entry[0];
                final double value = entry[1];

                CLOSE_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_MAP.put(key, value);
            });
        }

        public static final InterpolatingDoubleTreeMap FAR_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_MAP = 
        new InterpolatingDoubleTreeMap();

        static {
            Arrays.stream(FAR_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_ARRAY).forEach(entry -> {
                final double key = entry[0];
                final double value = entry[1];

                FAR_ZONE_DISTANCE_FEET_TO_MOTOR_FPS_MAP.put(key, value);
            });
        }
    }

    public static class FeederConstants {
        public static final double DEFAULT_FEED_SPEED = -1;

        public static final double DEFAULT_CURRENT_LIMIT = 70;

        public static final AudioConfigs AUDIO_CONFIGS = new AudioConfigs().withBeepOnBoot(false)
                .withBeepOnConfig(false).withAllowMusicDurDisable(true);

        public static final CurrentLimitsConfigs DEFAULT_CURRENT_LIMITS_CONFIGS = new CurrentLimitsConfigs()
                .withStatorCurrentLimitEnable(true)
                .withStatorCurrentLimit(DEFAULT_CURRENT_LIMIT);

        public static final MotorOutputConfigs DEFAULT_MOTOR_OUTPUT_CONFIGS = new MotorOutputConfigs()
                .withPeakForwardDutyCycle(DEFAULT_FEED_SPEED)
                .withPeakReverseDutyCycle(-DEFAULT_FEED_SPEED)
                .withNeutralMode(NeutralModeValue.Coast);
    }

    public static class ClimberConstants {
        // TODO: find values for climber
        public static final double DEFAULT_CURRENT_LIMIT = 0;

        public static final CurrentLimitsConfigs DEFAULT_CURRENT_LIMITS_CONFIGS = new CurrentLimitsConfigs()
                .withStatorCurrentLimitEnable(true)
                .withStatorCurrentLimit(DEFAULT_CURRENT_LIMIT);

        public static final MotorOutputConfigs DEFAULT_MOTOR_OUTPUT_CONFIGS = new MotorOutputConfigs();
    }

    public static class Directories {
        public static final String DEPLOY_DIRECTORY = Filesystem.getDeployDirectory().getPath();

        public static final String SONGS_DIRECTORY = String.format("%s/%s", DEPLOY_DIRECTORY, "midi");
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
