package frc.robot.commands.drive.auto.direct;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import frc.robot.commands.LoggingWrapperCommand;
import frc.robot.subsystems.drivebase.DirectDrivebase;

public class DriveDirectUntil extends LoggingWrapperCommand {
    public DriveDirectUntil(
        DirectDrivebase drivebase, 
        Supplier<ChassisSpeeds> input, 
        BooleanSupplier isFinishedSupplier
    ) {
        super(new FunctionalCommand(
            () -> {}, 
            () -> drivebase.driveWithSpeeds(input.get()), 
            interrupted -> {}, 
            isFinishedSupplier, 
            drivebase
        ));
    }
}
