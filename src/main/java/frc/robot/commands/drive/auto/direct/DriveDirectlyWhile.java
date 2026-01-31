package frc.robot.commands.drive.auto.direct;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import frc.robot.subsystems.drivebase.DirectDrivebase;

public class DriveDirectlyWhile extends FunctionalCommand {
    public DriveDirectlyWhile(
        DirectDrivebase drivebase, 
        Supplier<ChassisSpeeds> input, 
        BooleanSupplier isFinishedSupplier
    ) {
        super(
            () -> {}, 
            () -> drivebase.driveWithSpeeds(input.get()), 
            interrupted -> {}, 
            isFinishedSupplier, 
            drivebase
        );
    }
}
