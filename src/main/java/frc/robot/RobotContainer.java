// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.GameInfo;
import frc.robot.subsystems.Indicator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.indication.LED;
import frc.robot.subsystems.legacy.SwerveSubsystem;
import frc.robot.teleop.command.TeleopCommands;
import frc.robot.teleop.controller.ControlContext;
import frc.robot.teleop.controller.DriverController;
import frc.robot.teleop.controller.DriverXboxController;
import frc.robot.teleop.controller.OperatorXboxController;
import frc.robot.teleop.controller.TeleopController;
import frc.robot.teleop.controller.TeleopDriveInputs;

import java.io.File;
import org.littletonrobotics.junction.Logger; // TODO: Figure it out

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Rizz up the ops

  private final DriverController driverController = new DriverXboxController();
  private final TeleopController operatorController = new OperatorXboxController();

  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem drivebase =
      new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));

  private final GameInfo gameInfo = new GameInfo();

  private final Indicator indicator = new Indicator(gameInfo, new LED());

  private final Intake intake = new Intake();

  // Establish a Sendable Chooser that will be able to be sent to the
  // SmartDashboard, allowing
  // selection of desired auto
  private final SendableChooser<Command> autoChooser;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Rizz up the ops
    Rizzler.rizz();

    // Configure the trigger bindings
    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(true);

    // Create the NamedCommands that will be used in PathPlanner
    NamedCommands.registerCommand("test", Commands.print("I EXIST"));

    // Have the autoChooser pull in all PathPlanner autos as options
    autoChooser = AutoBuilder.buildAutoChooser();

    // Set the default auto (do nothing)
    autoChooser.setDefaultOption("Do Nothing", Commands.none());

    // Add a simple auto option to have the robot drive forward for 1 second then
    // stop
    autoChooser.addOption("Drive Forward", drivebase.driveForward().withTimeout(1));

    // Put the autoChooser on the SmartDashboard
    SmartDashboard.putData("Auto Chooser", autoChooser);

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in
   * {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings() {
    final ControlContext controlContext =
        new ControlContext(Robot.isSimulation(), DriverStation.isTest());

    final TeleopDriveInputs driveInputs = driverController.getTeleopDriveInputs(drivebase);

    drivebase.setDefaultCommand(
        drivebase.driveFieldOriented(driveInputs.getDefaultInputStream(drivebase, controlContext)));

    final TeleopCommands teleopCommands =
        new TeleopCommands(indicator, drivebase, intake, driveInputs);

    teleopCommands.bindToController(driverController, controlContext);
    teleopCommands.bindToController(operatorController, controlContext);

    if (controlContext.isSimulation()) {
      Pose2d target = new Pose2d(new Translation2d(1, 4), Rotation2d.fromDegrees(90));
      // drivebase.getSwerveDrive().field.getObject("targetPose").setPose(target);
      driveInputs.driveDirectAngleKeyboard().driveToPose(() -> target,
          new ProfiledPIDController(5, 0, 0, new Constraints(5, 2)), new ProfiledPIDController(5, 0,
              0, new Constraints(Units.degreesToRadians(360), Units.degreesToRadians(180))));
    }

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Pass in the selected auto from the SmartDashboard as our desired autnomous
    // commmand
    return autoChooser.getSelected();
  }

  public void setMotorBrake(boolean brake) {
    drivebase.setMotorBrake(brake);
  }

  /**
   * Run once when Robot is enabled in teleop in driverstation
   *
   * @return void
   */
  public void teleopInit() {

  }

  public void autoInit() {
    // led.blink("default");
  }

  /**
   * Run once when Robot is disabled in driverstation
   * 
   * @return void
   */
  public void disabledInit() {
    // PLACEHOLDER (SUBSYSTEM CONTAINING KRAKENS, SEE LEBRONAVATOR 2025 FOR
    // EX).playSong("BlueLobster"); TODO: Add back soon
  }

  // Periodically do things during teleop
  public void teleopPeriodic() {
    Pose2d currentPose = drivebase.getPose();
    Logger.recordOutput("MyPose2d", currentPose);
  }

  /**
   * Run every cycle when the robot is disabled in driverstation
   * 
   * @return void
   */
  public void disabledPeriodic() {

  }

  // TODO: Reimplement the folowing vvvv
  // public void driveNormal() {
  // System.out.println("Slow mode: disabled");
  // slowMode = 1.0;
  // }

  // public void driveSlow() {
  // System.out.println("Slow mode Activated");
  // slowMode = slowModeSpeed;
  // }
}
