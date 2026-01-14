// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.io.File;
import org.littletonrobotics.junction.Logger;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.Vision;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import frc.robot.commands.Helpers.AutoAlignment;
import frc.robot.commands.Helpers.DriveBasePoseAdjust;
import frc.robot.commands.Helpers.DriveBaseRotationAdjust;
import frc.robot.commands.Led.Rainbow;
import frc.robot.commands.teleop.*;
import swervelib.SwerveInputStream;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

  // Replace with CommandPS4Controller or CommandJoystick if needed
  final         CommandXboxController driverXbox = new CommandXboxController(0);
  final         CommandXboxController operatorXbox = new CommandXboxController(1);
  final         CommandXboxController coachXbox = new CommandXboxController(2);

  
  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem       drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),"swerve/"));
  //private final CaprisonCommands visionCommands = new CaprisonCommands();
  private final Bluetooth led = new Bluetooth();

  // Command Classes
  private final OperatorCommands opCommands = new OperatorCommands(led, drivebase); 

  private double slowModeSpeed = 0.5;
  private Double slowMode = 1.0;


  //private final PhotonCamera photon_camera = new PhotonCamera("Camera_Front");

  //private final LimeLight Limelight = new LimeLight();
  //private final PhotonCamera apriltagCam = new PhotonCamera("Camera_Front");
  // Create pathplanner auto chooser                                                                              
  private final SendableChooser<Command> autoChooser;



  /**
   * Converts driver input into a field-relative ChassisSpeeds that is controlled by angular velocity.
   */
  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                () -> driverXbox.getLeftY() * -1 * slowMode,
                                                                () -> driverXbox.getLeftX() * -1 * slowMode)
                                                            .withControllerRotationAxis(driverXbox::getRightX)
                                                            .deadband(OperatorConstants.DEADBAND)
                                                            //.scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);

  /**
   * Clone's the angular velocity input stream and converts it to a fieldRelative input stream.
   */
  SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis(driverXbox::getRightX,
                                                                                             driverXbox::getRightY)
                                                           .headingWhile(true);

  /**
   * Clone's the angular velocity input stream and converts it to a robotRelative input stream.
   */
  SwerveInputStream driveRobotOriented = driveAngularVelocity.copy().robotRelative(true)
                                                             .allianceRelativeControl(false);

  SwerveInputStream driveAngularVelocityKeyboard = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                        () -> -driverXbox.getLeftY(),
                                                                        () -> -driverXbox.getLeftX())
                                                                    .withControllerRotationAxis(() -> driverXbox.getRawAxis(
                                                                        2))
                                                                    .deadband(OperatorConstants.DEADBAND)
                                                                    //.scaleTranslation(0.8)
                                                                    .allianceRelativeControl(true);
  // Derive the heading axis with math!
  SwerveInputStream driveDirectAngleKeyboard     = driveAngularVelocityKeyboard.copy()
                                                                               .withControllerHeadingAxis(() ->
                                                                                                              Math.sin(
                                                                                                                  driverXbox.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2),
                                                                                                          () ->
                                                                                                              Math.cos(
                                                                                                                  driverXbox.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2))
                                                                               .headingWhile(true);


 
  /**

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   * @see edu.wpi.first.wpilibj2.command.Commands
   * @see com.pathplanner.lib.auto.AutoBuilder;
   * 
   * @version 1.1
   */
  public RobotContainer()
  {
    // Rizz up the ops
    Rizzler.rizz();

    // Blink color
    led.scroll("orange");

    //photon_camera.

    // Build an Pathplanner auto chooser. This will use Commands.none() as the default option.
    configureNamedCommands(); // Setup Pathplanner Commands
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);

    // Configure the trigger bindings
    driveNormal();
    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(true);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   * 
   * @return void
   * @version 1.0
   */
  private void configureBindings()
  {
    //Command driveFieldOrientedDirectAngle      = drivebase.driveFieldOriented(driveDirectAngle);
    Command driveFieldOrientedAnglularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);
    //Command driveRobotOrientedAngularVelocity  = drivebase.driveFieldOriented(driveRobotOriented);
    //Command driveSetpointGen = drivebase.driveWithSetpointGeneratorFieldRelative(
     //   driveDirectAngle);
    //Command driveFieldOrientedDirectAngleKeyboard      = drivebase.driveFieldOriented(driveDirectAngleKeyboard);
    Command driveFieldOrientedAnglularVelocityKeyboard = drivebase.driveFieldOriented(driveAngularVelocityKeyboard);
    //Command driveSetpointGenKeyboard = drivebase.driveWithSetpointGeneratorFieldRelative(
     //   driveDirectAngleKeyboard);

    if (RobotBase.isSimulation())
    {
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocityKeyboard);
      driverXbox.start().onTrue(Commands.runOnce(() -> drivebase.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
      driverXbox.button(1).whileTrue(drivebase.sysIdDriveMotorCommand());
    } else
    {
      /**  driver Xbox */
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);

      driverXbox.start().whileTrue(Commands.runOnce(Vision::updateShuffleboard));


      driverXbox.a().whileTrue(Commands.runOnce(drivebase::zeroGyroWithAlliance));
    
      driverXbox.x().whileTrue(new Rainbow(led, 10));
      driverXbox.y().whileTrue(new InstantCommand( () -> {
        System.out.println("Robot Pose: " +drivebase.getPose()); }));

      driverXbox.leftTrigger().whileTrue(new AutoAlignment(drivebase, led, Constants.DrivebaseConstants.y_offset_left, false));
      driverXbox.rightTrigger().whileTrue(new AutoAlignment(drivebase, led, Constants.DrivebaseConstants.y_offset_right, false));
      driverXbox.rightBumper().whileTrue(Commands.runEnd(this::driveSlow, this::driveNormal));

      /** Operator Xbox */


    } 
    /*
    if (DriverStation.isTest())
    {
      drivebase.setDefaultCommand(driveFieldOrientedDirectAngle); // Overrides drive command above!

      driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      driverXbox.y().whileTrue(drivebase.driveToDistanceCommand(1.0, 0.2));
      driverXbox.start().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      driverXbox.back().whileTrue(drivebase.centerModulesCommand());
      driverXbox.leftBumper().onTrue(Commands.none());
      driverXbox.rightBumper().onTrue(Commands.none());
    } else
    {
      driverXbox.a().onTrue((Commands.runOnce(drivebase::zeroGyro)));
      driverXbox.x().onTrue(Commands.runOnce(drivebase::addFakeVisionReading));
      driverXbox.b().whileTrue(
          drivebase.driveToPose(
              new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
                              );
      driverXbox.start().whileTrue(Commands.none());
      driverXbox.back().whileTrue(Commands.none());
      driverXbox.leftBumper().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      driverXbox.rightBumper().onTrue(Commands.none());
    } */
  }

  /**
   * Defines Named Commands for use with Pathplanner
   * Should include all available commands on in this code
   * 
   * @return void
   * @version 1.0
   * 
   * @see com.pathplanner.lib.auto.NamedCommands
   */
  private void configureNamedCommands() {
    /* Vision - Requires vision readings from either apriltags or gamepeices, use near middle through end of auto*/
    /* Manual - Only for certain edge cases, use Automated controls for most autos */
    /* Rare Use Case - There is no thinkable reason to use these */

    // AutoOperator
  
    // Elevevator

    // Led
    NamedCommands.registerCommand("Rainbow", new Rainbow(led, 5.0));

    // Elevator
    //NamedCommands.registerCommand("ArmToStow", new ElevatorMove(climber));



    /*  Endafector */
    

    // Drivebase
    NamedCommands.registerCommand("DriveBaseRotationAdjust", new DriveBaseRotationAdjust(drivebase, led)); // Vision
    NamedCommands.registerCommand("DriveBasePoseAdjustL", new AutoAlignment(drivebase, led, Constants.DrivebaseConstants.y_offset_left, true));
    NamedCommands.registerCommand("DriveBasePoseAdjustR", new AutoAlignment(drivebase, led,  Constants.DrivebaseConstants.y_offset_right, false));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   * @version 1.1
   */
  public Command getAutonomousCommand()
  {
    // Pathplanner selected auto will be run in autonomous
    return autoChooser.getSelected();
  }

  /**
   * Run when robot is ready to drive
   *
   * @return void
   * @version 1.1
   */
  public void setDriveMode()
  {
    led.scroll("default");
    configureBindings();
  }

  /**
   * Sets all motors in the drivebase to brake mode or coast mode
   * @see frc.robot.subsystems.swervedrive.old.SwerveSubsystem.setMotorBrake
   * 
   * @param boolean turn brake mode on or off
   * @return void
   * @version 1.0
   */
  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }

  /** 
   * Changes the Led Color to match the selected alliance
   * in driverstation. Should be called periodically when disabled
   * 
   * @return void
   * @version 1.0
   * 
   * @see edu.wpi.first.wpilibj.DriverStation.getAlliance
   * @see frc.robot.subsystems.Bluetooth
   * */
  public void matchLedWithAlliance() {
    var alliance = DriverStation.getAlliance();
    if (alliance.isPresent())
    {
      if(alliance.get() == DriverStation.Alliance.Red) {
        led.color("red");
        led.setDefaultColor("red");
      }
      else {
        led.color("blue");
        led.setDefaultColor("blue");
      }
    }
  }

  /**
   * Run once when Robot is enabled in teleop in driverstation
   *
   * @return void
   */
  public void teleopInit() {
    //opCommands.AutoStow().schedule(); //  Move the elevator to the Stow position, and run endefector
  }

  public void autoInit() {
    led.blink("default");
  }

  /**
   * Run once when Robot is disabled in driverstation
   *  
   * @return void
   */
  public void disabledInit() {
    SmartDashboard.putData("Restart Photonvision", Commands.runOnce(Vision::restartPhotonvision));
    SmartDashboard.putData("Reboot Camera", Commands.runOnce(Vision::rebootPhotonvision));
    //elevator.playSong("BlueLobster"); // Play Amoung us theme to pass the time TODO: Put this back when we have motors to play it
  }


// Periodically do things during teleop
  public void teleopPeriodic(){
     Pose2d currentPose = drivebase.getPose();
    Logger.recordOutput("MyPose2d", currentPose);
/*Logger.recordOutput("MyPose2dArray", poseA, poseB);
Logger.recordOutput("MyPose2dArray", new Pose2d[] { poseA, poseB }); TODO: Log the ODEM */

  }
  /**
   * Run every cycle when the robot is disabled in driverstation
   * 
   * @return void
   */
  public void disabledPeriodic() {
    matchLedWithAlliance(); // Change Led color depending on which alliance the robot is on
  }

  public void driveNormal() {
    System.out.println("Slow mode: disabled");
    slowMode = 1.0;
  }

  public void driveSlow() {
    System.out.println("Slow mode: true");
    slowMode = slowModeSpeed;
  }
}
