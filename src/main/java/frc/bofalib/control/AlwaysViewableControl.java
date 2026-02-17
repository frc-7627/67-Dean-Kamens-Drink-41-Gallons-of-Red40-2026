package frc.bofalib.control;

import java.util.Optional;

public interface AlwaysViewableControl<Control> extends ViewableControl<Control> {
    Control getCurrentControl();

    @Override
    default Optional<Control> getCurrentControlIfPresent() {
        return Optional.of(getCurrentControl());
    }
}
