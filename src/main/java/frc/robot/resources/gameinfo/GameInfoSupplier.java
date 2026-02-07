package frc.robot.resources.gameinfo;

import frc.bofalib.subsystem.SharedSubsystem;

/**
 * Interface that combines the GeneralGameInfoSupplier and
 * SpecificGameInfoSupplier interfaces.
 * Contains all information and states about the game.
 */
public interface GameInfoSupplier extends GeneralGameInfoSupplier, SpecificGameInfoSupplier, SharedSubsystem {
    /**
     * Bind the action for when an alliance has been set by the driver station.
     * 
     * @param action the action.
     */
    void onAllianceSet(Runnable action);

    static GameInfoSupplier create() {
        return new GameInfoSupplierImpl();
    }
}
