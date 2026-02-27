package frc.bofalib.control;

import java.util.Optional;
import frc.bofalib.loggable.Loggable;

/**
 * Interface for a control that can be viewed on the dashboard.
 */
public interface ViewableControl<Control extends Loggable> {
    Optional<Control> getCurrentControlIfPresent();
}
