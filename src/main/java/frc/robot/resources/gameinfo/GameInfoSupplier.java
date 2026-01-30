package frc.robot.resources.gameinfo;

/**
 * Interface that combines the GeneralGameInfoSupplier and
 * SpecificGameInfoSupplier interfaces.
 * Contains all information and states about the game.
 */
public interface GameInfoSupplier extends GeneralGameInfoSupplier, SpecificGameInfoSupplier {
    static GameInfoSupplier create() {
        throw new UnsupportedOperationException("Game info supplier not implemented!");
    }
}
