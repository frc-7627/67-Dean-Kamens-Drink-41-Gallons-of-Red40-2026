// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;
import org.littletonrobotics.urcl.URCL;

import com.ctre.phoenix6.SignalLogger;
import com.pathplanner.lib.pathfinding.LocalADStar;
import com.pathplanner.lib.pathfinding.Pathfinding;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the TimedRobot documentation. If you change the name of this class or the package after creating this
 * project, you must also update the build.gradle file in the project.
 * 
 * @see org.littletonrobotics.junction.LoggedRobot
 */
public class Robot extends LoggedRobot
{
  



    //private final SwerveSubsystem       drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve/"));
  private static Robot   instance;
  private        Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private Timer disabledTimer;

  public Robot()
  {


    Logger.recordMetadata("ProjectName", "MyProject"); // Set a metadata value

if (isReal()) {
    Logger.addDataReceiver(new WPILOGWriter()); // Log to a USB stick ("/U/logs")
    Logger.addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
} else {
    setUseTiming(false); // Run as fast as possible
    String logPath = LogFileUtil.findReplayLog(); // Pull the replay log from AdvantageScope (or prompt the user)
    Logger.setReplaySource(new WPILOGReader(logPath)); // Read replay log
    Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim"))); // Save outputs to a new log
}

Logger.start(); // Start logging! No more data receivers, replay sources, or metadata values may be added.
    instance = this;
    
    //LoggerSetup();
  }

  /**
   * Returns the objects instance
   * @return frc.robot.Robot
   * 
   * @version 1.0
   */
  public static Robot getInstance()
  {
    return instance;
  }


  /**
   * Sets up the advatagekit logger
   * @see org.littletonrobotics.junction.Logger
   * @return void
   * 
   * @version 1.1
   */
  //public void LoggerSetup() {
    //

    //if (isReal()) {
        //Logger.addDataReceiver(new WPILOGWriter()); // Log to a USB stick ("/U/logs")
    //    Logger.addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
        //new PowerDistribution(1, ModuleType.kRev); // Enables power distribution logging
    //} else {
    //    setUseTiming(false); // Run as fast as possible
    //    String logPath = LogFileUtil.findReplayLog(); // Pull the replay log from AdvantageScope (or prompt the user)
    //    Logger.setReplaySource(new WPILOGReader(logPath)); // Read replay log
    //    Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim"))); // Save outputs to a new log
  //  }

    //Logger.start(); // Start logging! No more data receivers, replay sources, or metadata values may be added.
 // }

  /**
   * This function is run when the robot is first started up and should be used for any initialization code.
   * 
   */


  @Override
  public void robotInit()
  {
    // Configure PathFinder for driveToPose
    Pathfinding.setPathfinder(new LocalADStar());

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    // Create a timer to disable motor brake a few seconds after disable.  This will let the robot stop
    // immediately when disabled, but then also let it be pushed more 
    disabledTimer = new Timer();

    if (isSimulation())
    {
      DriverStation.silenceJoystickConnectionWarning(true);
    }

    Logger.recordMetadata("ProjectName", "MyProject"); // Set a metadata value
    // Set up data receivers & replay source
        switch (Constants.currentMode) {
            case REAL:
                // Running on a real robot, log to a USB stick ("/U/logs")
                Logger.addDataReceiver(new WPILOGWriter());// Log to a USB stick ("/U/logs")
                Logger.addDataReceiver(new NT4Publisher());// Publish data to NetworkTables 
                break;

            case SIM:
                // Running a physics simulator, log to NT
                Logger.addDataReceiver(new NT4Publisher());
                break;

            case REPLAY:
                // Replaying a log, set up replay source
                setUseTiming(false); // Run as fast as possible
                String logPath = LogFileUtil.findReplayLog();// Pull the replay log from AdvantageScope (or prompt the user)
                Logger.setReplaySource(new WPILOGReader(logPath));// Read replay log
                Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));// Save outputs to a new log
                break;
        }

        // See http://bit.ly/3YIzFZ6 for more information on timestamps in AdvantageKit.
        // Logger.disableDeterministicTimestamps()

        SignalLogger.enableAutoLogging(true);

        // Start AdvantageKit logger
        DataLogManager.start();
        URCL.start();

        // If logging only to DataLog
        URCL.start(DataLogManager.getLog());
  }


  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics that you want ran
   * during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic()
  {
   /*  Pose2d currentPose = drivebase.getPose();
    Logger.recordOutput("MyPose2d", currentPose); todo: diffrently  */
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit()
  {
    m_robotContainer.setMotorBrake(true);
    m_robotContainer.disabledInit();
    disabledTimer.reset();
    disabledTimer.start();
  }

  @Override
  public void disabledPeriodic()
  {
    if (disabledTimer.hasElapsed(Constants.DrivebaseConstants.WHEEL_LOCK_TIME))
    {
      m_robotContainer.setMotorBrake(false);
      disabledTimer.stop();
    }

    m_robotContainer.disabledPeriodic();
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit()
  {
    m_robotContainer.setMotorBrake(true);
    m_robotContainer.autoInit();
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null)
    {
      m_autonomousCommand.schedule();
      //m_robotContainer.setGyroOffset();
      //m_robotContainer.setHeadingCorrection();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic()
  {
  }

  @Override
  public void teleopInit()
  {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null)
    {
      m_autonomousCommand.cancel();
    } else
    {
      CommandScheduler.getInstance().cancelAll();
    }
    m_robotContainer.setDriveMode();
    //m_robotContainer.allianceGryoReset();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic()
  {
  }

  @Override
  public void testInit()
  {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    m_robotContainer.setDriveMode();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic()
  {
  }

  /**
   * This function is called once when the robot is first started up.
   */
  @Override
  public void simulationInit()
  {
  }

  /**
   * This function is called periodically whilst in simulation.
   */
  @Override
  public void simulationPeriodic()
  {
  }
}
