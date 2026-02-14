// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.drive.direct.DriveWithInput;
import frc.robot.setup.auto.AutoChooser;
import frc.robot.setup.teleop.CommandContext;
import frc.robot.setup.teleop.DriverController;
import frc.robot.setup.teleop.OperatorController;
import frc.robot.subsystems.indication.Indicator;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.pathplanner.PathPlannerConfigException;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.agitator.Agitator;
import frc.robot.subsystems.controlstate.GlobalControlState;
import frc.robot.subsystems.controlstate.GlobalControlState.ControlState;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.subsystems.drivebase.DrivebaseInitException;
import frc.robot.subsystems.feeder.Feeder;
import frc.robot.subsystems.gameinfo.GameInfoSupplier;
import frc.robot.subsystems.launcher.Launcher;
import org.littletonrobotics.junction.Logger;
import com.ctre.phoenix6.hardware.Pigeon2;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // Rizz up the ops

    private final DriverController driverController;
    private final OperatorController operatorController;

    // The robot's subsystems and resources are defined here...
    private final Vision vision;

    private final GameInfoSupplier gameInfoSupplier;

    Pigeon2 pigeon = new Pigeon2(1); 

    private final Drivebase drivebase;

    private final Indicator indicator;

    private final Intake intake;

    private final Feeder feeder;

    private final Agitator hopper;

    private final Launcher launcher;

    private final GlobalControlState globalControlState;

    private final CommandContext commandContext;

    private final AutoChooser autoChooser;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() throws RobotInitException {
        this.driverController = DriverController.create();
        this.operatorController = OperatorController.create();

        this.vision = Vision.create();

        this.gameInfoSupplier = GameInfoSupplier.create();

        try {
            this.drivebase = Drivebase.create(vision, gameInfoSupplier);
        } catch (DrivebaseInitException cause) {
            throw new RobotInitException("Could not initialize drivebase!", cause);
        }

        this.indicator = Indicator.create(gameInfoSupplier);

        // intake fuel
        this.intake = Intake.create();

        // serialize fuel into launcher
        this.feeder = Feeder.create();

        // agitate fuel in robot
        this.hopper = Agitator.create();

        // release and score fuel
        this.launcher = Launcher.create();

        this.globalControlState = GlobalControlState.create();

        this.commandContext = new CommandContext(
            indicator,
            drivebase,
            intake,
            launcher,
            feeder,
            hopper,
            globalControlState,
            gameInfoSupplier,
            driverController.getInput(drivebase)
        );

        try {
            this.autoChooser = AutoChooser.create(commandContext, drivebase.getPathPlannerConfigurator().get());
        } catch (PathPlannerConfigException cause) {
            throw new RobotInitException("Could not configure autos!", cause);
        }

        indicator.indicateStartup();

        setupTeleop();
    }

    /**
     * Setup teleop stuff.
     */
    private void setupTeleop() {
        DriverStation.silenceJoystickConnectionWarning(true);

        drivebase.setDefaultCommand(
                new DriveWithInput(drivebase, driverController.getInput(drivebase)));

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
