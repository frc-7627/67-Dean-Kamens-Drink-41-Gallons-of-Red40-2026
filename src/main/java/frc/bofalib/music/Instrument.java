package frc.bofalib.music;

import com.ctre.phoenix6.Orchestra;

@FunctionalInterface
public interface Instrument {
    void addToOrchestra(Orchestra orchestra);
}
