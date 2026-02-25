// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.VisionConstants.VISION_ENABLED;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import frc.bofalib.generic.control.ControlCommand;
import frc.bofalib.generic.music.MusicalSubsystem;
import frc.robot.commands.RobotSongCommand;
import frc.robot.setup.auto.AutoChooser;
import frc.robot.setup.teleop.CommandContext;
import frc.robot.setup.teleop.DriverController;
import frc.robot.setup.teleop.OperatorController;
import frc.robot.subsystems.misc.controlstate.GlobalControlState;
import frc.robot.subsystems.misc.controlstate.GlobalControlState.ControlState;
import frc.robot.subsystems.misc.indication.Indicator;
import frc.robot.subsystems.shared.gameinfo.GameInfoSupplier;
import frc.robot.subsystems.shared.vision.Vision;
import frc.robot.subsystems.shared.vision.VisionMeasurementsSupplier;
import frc.robot.subsystems.controllable.agitator.Agitator;
import frc.robot.subsystems.controllable.drivebase.DriveControl;
import frc.robot.subsystems.controllable.drivebase.Drivebase;
import frc.robot.subsystems.controllable.feeder.Feeder;
import frc.robot.subsystems.controllable.intake.Intake;
import frc.robot.subsystems.controllable.launcher.Launcher;
import frc.robot.subsystems.controllable.launcher.LauncherControl;
import frc.robot.subsystems.controllable.swivel.Swivel;

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
    private final Optional<Vision> visionOptional = VISION_ENABLED ? 
        Optional.of(Vision.create()) : 
        Optional.empty()
    ;

    private final GameInfoSupplier gameInfoSupplier = GameInfoSupplier.create();

    private final Drivebase drivebase = Drivebase.create(
        visionOptional.map(vision -> (VisionMeasurementsSupplier) vision), 
        gameInfoSupplier
    );

    private final Indicator indicator = Indicator.create(
        gameInfoSupplier
    );

    private final Intake intake = Intake.create();

    private final Swivel swivel = Swivel.create();

    private final Feeder feeder = Feeder.create();

    private final Agitator hopper = Agitator.create();

    private final Launcher launcher = Launcher.create();

    private final GlobalControlState globalControlState = GlobalControlState.create();

    private final DriveControl inputDriveControl = driverController.getInputDriveControl(
        drivebase::getInputDriveControl
    );

    private final Collection<? extends MusicalSubsystem> musicalSubsystems = List.of(
        launcher,
        intake,
        feeder
    );

    private final CommandContext commandContext = new CommandContext(
        indicator,
        drivebase,
        intake,
        swivel,
        launcher,
        feeder,
        hopper,
        globalControlState,
        gameInfoSupplier,
        inputDriveControl,
        musicalSubsystems
    );

    private final AutoChooser autoChooser = AutoChooser.create(commandContext);

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

        launcher.setDefaultCommand(new ConditionalCommand(
            new ControlCommand<>(launcher, LauncherControl.ACTIVE_IDLE), 
            new ControlCommand<>(launcher, LauncherControl.INACTIVE_IDLE), 
            gameInfoSupplier::willHubActivate
        ));

        globalControlState.onNewControlState(this::bindControllers);
        bindControllers(ControlState.NORMAL);
    }

    private void bindControllers(ControlState controlState) {
        driverController.bindAll(commandContext, controlState);
        operatorController.bindAll(commandContext, controlState);
    }

    private void playRandomSong() {
        CommandScheduler.getInstance().schedule(
            new RobotSongCommand(musicalSubsystems, RobotSong.getRandomSong())
        );
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // Pass in the selected auto from the SmartDashboard as our desired autnomous commmand
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
        playRandomSong();
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
}
