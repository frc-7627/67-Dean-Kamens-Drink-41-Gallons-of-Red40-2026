package frc.robot.resources.gameinfo;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public interface GeneralGameInfoSupplier {
    Alliance getAlliance();

    void onAllianceSet(Runnable action);
}
