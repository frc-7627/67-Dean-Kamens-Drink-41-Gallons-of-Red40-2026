package frc.bofalib;

import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj.Timer;

public final class Throttler {
    private final Timer timer = new Timer();
    private final Time period;

    public Throttler(Time period) {
            this.period = period;

        timer.start();
    }

    public Throttler(Frequency frequency) {
        this(frequency.asPeriod());
    }

    public boolean isReady() {
        return timer.hasElapsed(period);
    }

    public void reset() {
        timer.reset();
    }

    public void execute(Runnable action) {
        if (isReady()) {
            action.run();

            reset();
        }
    }
}
