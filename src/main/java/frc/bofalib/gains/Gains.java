package frc.bofalib.gains;

@FunctionalInterface
public interface Gains {
    void setGain(GainSelection gain, double value);
}
