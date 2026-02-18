package frc.bofalib.control;

import java.util.Optional;
import frc.bofalib.loggable.Loggable;

public interface ViewableControl<Control extends Loggable> {
    Optional<Control> getCurrentControlIfPresent();
}
