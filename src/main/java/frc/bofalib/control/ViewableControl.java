package frc.bofalib.control;

import java.util.Optional;

public interface ViewableControl<Control> {
    Optional<Control> getCurrentControlIfPresent();
}
