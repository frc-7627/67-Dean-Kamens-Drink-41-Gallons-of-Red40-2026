package frc.bofalib.generic.music;

import com.ctre.phoenix6.Orchestra;

public interface UniInstrument<FirstInstrument extends Instrument> extends Instrument {
    FirstInstrument getFirstInstrument();

    @Override
    default void addToOrchestra(Orchestra orchestra) {
        getFirstInstrument().addToOrchestra(orchestra);
    }
}
