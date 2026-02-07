package frc.bofalib;

import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj.Timer;

public final class Util {
    public static String mockName(String name) {
        return name + " (mock)";
    }

    public static Runnable throttle(Runnable action, Frequency frequency) {
        return throttle(action, frequency.asPeriod());
    }

    public static Runnable throttle(Runnable action, Time period) {
        return new Runnable() {
            private final Timer timer = new Timer();

            {
                timer.start();
            }

            @Override
            public void run() {
                if (timer.hasElapsed(period)) {
                    action.run();
                    timer.reset();
                }
            }
        };
    }
}
