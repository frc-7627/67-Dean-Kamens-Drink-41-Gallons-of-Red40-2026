package frc.bofalib.dashboard;

interface Storing<Stored> extends Pushing<Stored>, Pulling<Stored> {
    boolean checkStored(Stored stored);

    @Override
    default boolean checkPushed(Stored pushed) {
        return checkStored(pushed);
    }

    @Override
    default boolean checkPulled(Stored pulled) {
        return checkStored(pulled);
    }
}
