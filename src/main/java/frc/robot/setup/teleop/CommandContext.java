package frc.robot.setup.teleop;

import frc.robot.subsystems.indication.Indicator;
import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.agitator.Agitator;
import frc.robot.subsystems.controlstate.ControlStateToggler;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.subsystems.gameinfo.GameInfoSupplier;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.feeder.Feeder;

public record CommandContext(
        Indicator indicator,
        Drivebase drivebase,
        Intake intake,
        Launcher launcher,
        Feeder feeder,
        Agitator hopper,
        ControlStateToggler controlStateToggler,
        GameInfoSupplier gameInfoSupplier,
        Supplier<ChassisSpeeds> input) {
}
