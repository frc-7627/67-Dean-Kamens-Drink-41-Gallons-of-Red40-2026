package frc.bofalib.generic.music;

import com.ctre.phoenix6.Orchestra;
import frc.bofalib.music.Instrument;

public interface UniInstrument<FirstInstrument extends Instrument> extends Instrument {
    FirstInstrument getFirstInstrument();

    @Override
    default void addToOrchestra(Orchestra orchestra) {
        getFirstInstrument().addToOrchestra(orchestra);
    }
}
