package frc.robot.subsystems.shared.gameinfo;

import frc.bofalib.subsystem.SharedSubsystem;

/**
 * Interface that combines the GeneralGameInfoSupplier and
 * SpecificGameInfoSupplier interfaces.
 * Contains all information and states about the game.
 */
public interface GameInfoSupplier extends GeneralGameInfoSupplier, SpecificGameInfoSupplier, SharedSubsystem {
    static GameInfoSupplier create() {
        return new GameInfoSupplierImpl();
    }
}
