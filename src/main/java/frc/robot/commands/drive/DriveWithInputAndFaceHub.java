package frc.robot.commands.drive;

import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.commands.drive.teleop.DriveWithInput;
import frc.robot.resources.gameinfo.SpecificGameInfoSupplier;
import frc.robot.subsystems.drivebase.Drivebase;

public class DriveWithInputAndFaceHub extends DriveWithInput {
        public DriveWithInputAndFaceHub(Drivebase drivebase,
                        SpecificGameInfoSupplier gameInfoSupplier, Supplier<ChassisSpeeds> input) {
                super(drivebase, () -> input.get().plus(drivebase.getRotationControl()
                                .apply(gameInfoSupplier.getHubPosition()
                                                .minus(drivebase.getPose().getTranslation())
                                                .getAngle()
                                                .minus(drivebase.getPose().getRotation()))));
        }
}
