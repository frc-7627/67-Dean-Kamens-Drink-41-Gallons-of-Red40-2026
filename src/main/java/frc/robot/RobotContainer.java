// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ControlCommand;
import frc.robot.setup.auto.AutoChooser;
import frc.robot.setup.teleop.CommandContext;
import frc.robot.setup.teleop.DriverController;
import frc.robot.setup.teleop.OperatorController;
import frc.robot.subsystems.indication.Indicator;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.controlstate.GlobalControlState;
import frc.robot.subsystems.controlstate.GlobalControlState.ControlState;
import frc.robot.subsystems.drivebase.DriveControl;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.subsystems.feeder.Feeder;
import frc.robot.subsystems.gameinfo.GameInfoSupplier;
import frc.robot.subsystems.hopper.Hopper;
import frc.robot.subsystems.launcher.Launcher;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // Rizz up the ops

    private final DriverController driverController = DriverController.create();
    private final OperatorController operatorController = OperatorController.create();

    // The robot's subsystems and resources are defined here...
    private final Vision vision = Vision.create();

    private final GameInfoSupplier gameInfoSupplier = GameInfoSupplier.create();

    private final Drivebase drivebase = Drivebase.create(
        vision, 
        gameInfoSupplier::getAlliance
    );

    private final Indicator indicator = Indicator.create(
        gameInfoSupplier
    );

    private final Intake intake = Intake.create();

    private final Feeder feeder = Feeder.create();

    private final Hopper hopper = Hopper.create();

    private final Launcher launcher = Launcher.create();

    private final GlobalControlState globalControlState = GlobalControlState.create();

    private final DriveControl inputDriveControl = driverController.getInputDriveControl(
        drivebase::getInputDriveControl
    );

    private final CommandContext commandContext = new CommandContext(
        indicator,
        drivebase,
        intake,
        launcher,
        feeder,
        hopper,
        globalControlState,
        gameInfoSupplier,
        inputDriveControl
    );

    private final AutoChooser autoChooser = AutoChooser.create();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        indicator.indicateStartup();

        setupTeleop();
    }

    /**
     * Setup teleop stuff.
     */
    private void setupTeleop() {
        DriverStation.silenceJoystickConnectionWarning(true);

        drivebase.setDefaultCommand(new ControlCommand<>(drivebase, inputDriveControl));

        globalControlState.onNewControlState(this::bindControllers);
        bindControllers(ControlState.NORMAL);
    }

    private void bindControllers(ControlState controlState) {
        driverController.bindAll(commandContext, controlState);
        operatorController.bindAll(commandContext, controlState);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // Pass in the selected auto from the SmartDashboard as our desired autnomous
        // commmand
        return autoChooser.get();
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
