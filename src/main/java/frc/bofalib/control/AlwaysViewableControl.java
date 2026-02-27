package frc.bofalib.control;

import java.util.Optional;
import frc.bofalib.loggable.Loggable;

/**
 * Interface for a control that can be viewed on the dashboard and is always
 * present.
 */
public interface AlwaysViewableControl<Control extends Loggable> extends ViewableControl<Control> {
    Control getCurrentControl();

    @Override
    default Optional<Control> getCurrentControlIfPresent() {
        return Optional.of(getCurrentControl());
    }
}
