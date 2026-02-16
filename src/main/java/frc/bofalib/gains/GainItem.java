package frc.bofalib.gains;

import java.util.Objects;

public final class GainItem {
    public final GainSelection selection;
    public final double defaultValue;

    public GainItem(GainSelection selection, double defaultValue) {
        this.selection = Objects.requireNonNull(selection);
        this.defaultValue = defaultValue;
    }
}
