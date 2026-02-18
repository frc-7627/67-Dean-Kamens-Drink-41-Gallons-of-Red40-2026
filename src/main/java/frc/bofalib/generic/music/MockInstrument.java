package frc.bofalib.generic.music;

import com.ctre.phoenix6.Orchestra;
import frc.bofalib.music.Instrument;

public interface MockInstrument extends Instrument {
    @Override
    default void addToOrchestra(Orchestra orchestra) {
        
    }
}
