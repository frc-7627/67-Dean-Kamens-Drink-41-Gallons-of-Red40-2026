package frc.robot.subsystems.drivebase;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drivebase.swerve.Swerve;
import frc.robot.subsystems.drivebase.swerve.SwerveConstructorException;
import frc.robot.subsystems.drivebase.swerve.SwerveDriveWrapper;
import frc.robot.subsystems.drivebase.vision.OldVision;

public class SwerveDrivebase extends SubsystemBase implements Drivebase {
    private final Swerve swerve;
    private final OldVision vision;

    public SwerveDrivebase(Alliance alliance) throws DrivebaseConstructorException {
        try {
            this.swerve = new SwerveDriveWrapper(alliance);
            this.vision = new OldVision(swerve.getField());
        } catch (SwerveConstructorException cause) {
            throw new DrivebaseConstructorException("Could not construct Swerve!", cause);
        }

    }

    @Override
    public void lock() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lock'");
    }

    @Override
    public void zeroGyro() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'zeroGyro'");
    }

    @Override
    public void resetOdometryToInitial() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetOdometryToInitial'");
    }

    @Override
    public void centerModules() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'centerModules'");
    }

    @Override
    public void driveWithSpeeds(ChassisSpeeds chassisSpeeds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'driveWithSpeeds'");
    }

    @Override
    public PathConstraints getPathConstraints() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPathConstraints'");
    }
}
