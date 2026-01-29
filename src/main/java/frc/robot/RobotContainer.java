// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.drive.manual.DriveWithInput;
import frc.robot.resources.gameinfo.GameInfoSupplier;
import frc.robot.resources.pathplanner.PathPlannerConfigException;
import frc.robot.resources.vision.Vision;
import frc.robot.resources.vision.VisionInitException;
import frc.robot.subsystems.Indicator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.subsystems.drivebase.DrivebaseInitException;
import frc.robot.teleop.command.TeleopCommands;
import frc.robot.teleop.controller.DriverController;
import frc.robot.teleop.controller.DriverXboxController;
import frc.robot.teleop.controller.OperatorXboxController;
import frc.robot.teleop.controller.TeleopController;

import org.littletonrobotics.junction.Logger;

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

    // The robot's subsystems and resources are defined here...
    private final Drivebase drivebase;

    private final Vision vision;

    private final GameInfoSupplier gameInfoSupplier;

    private final Indicator indicator;

    private final Intake intake;

    // Establish a Sendable Chooser that will be able to be sent to the
    // SmartDashboard, allowing
    // selection of desired auto
    private final SendableChooser<Command> autoChooser;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() throws RobotInitException {
        this.autoChooser = AutoBuilder.buildAutoChooser();

        this.gameInfoSupplier = GameInfoSupplier.create();

        // TODO
        this.indicator = null;

        // TODO
        this.intake = null;

        try {
            this.vision = Vision.create();
        } catch (VisionInitException cause) {
            throw new RobotInitException("Could not initialize vision!", cause);
        }

        try {
            this.drivebase = Drivebase.create(vision, gameInfoSupplier);
        } catch (DrivebaseInitException cause) {
            throw new RobotInitException("Could not initialize drivebase!", cause);
        }

        // Configure
        setupTeleop();

        try {
            setupAuto();
        } catch (PathPlannerConfigException cause) {
            throw new RobotInitException("Could not configure autos!", cause);
        }

        // Rizz up the ops
        Rizzler.rizz();
    }

    /**
     * Setup teleop stuff.
     */
    private void setupTeleop() {
        drivebase.setDefaultCommand(
                new DriveWithInput(drivebase, driverController.getInput(drivebase)));

        final TeleopCommands teleopCommands = new TeleopCommands(indicator, drivebase, intake);

        teleopCommands.bindToController(driverController);
        teleopCommands.bindToController(operatorController);

    }

    /**
     * Setup auto stuff.
     */
    private void setupAuto() throws PathPlannerConfigException {
        drivebase.getPathPlannerConfigurator().get().configureAndInit();

        DriverStation.silenceJoystickConnectionWarning(true);

        // Create the NamedCommands that will be used in PathPlanner
        NamedCommands.registerCommand("test", Commands.print("I EXIST"));

        // Set the default auto (do nothing)
        autoChooser.setDefaultOption("Do Nothing", Commands.none());

        // Add a simple auto option to have the robot drive forward for 1 second then
        // stop
        // TODO: replace
        // autoChooser.addOption("Drive Forward", drivebase.driveForward().withTimeout(1));

        // Put the autoChooser on the SmartDashboard
        SmartDashboard.putData("Auto Chooser", autoChooser);
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

    public void setBrake(boolean brake) {
        drivebase.setBrake(brake);
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
