package frc.robot.resources.gameinfo;

public interface GameInfoSupplier extends GeneralGameInfoSupplier, SpecificGameInfoSupplier {
    static GameInfoSupplier create() {
        throw new UnsupportedOperationException("Game info supplier not implemented!");
    }
}
