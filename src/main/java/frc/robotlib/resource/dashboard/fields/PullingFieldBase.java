package frc.robotlib.resource.dashboard.fields;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import frc.robotlib.resource.dashboard.PullingField;

abstract class PullingFieldBase<Pulled> extends SubdashboardBase implements PullingField<Pulled> {
    private final Predicate<Pulled> checkPulled;
    private final Consumer<Pulled> onPull;
    private Pulled currentPulled;

    protected PullingFieldBase(String superdashboardName, String fieldName,
            Predicate<Pulled> checkPulled, Consumer<Pulled> onPull, Pulled initialPulled) {
        super(superdashboardName, superdashboardName);

        this.checkPulled = Objects.requireNonNull(checkPulled);
        this.onPull = Objects.requireNonNull(onPull);

        if (checkPulled(Objects.requireNonNull(initialPulled))) {
            this.currentPulled = initialPulled;
            onPull(initialPulled);
            pushInitial(initialPulled);
        } else {
            throw new BadInitialValueError(initialPulled);
        }
    }

    @Override
    public final boolean checkPulled(Pulled pulled) {
        return checkPulled.test(pulled);
    }

    @Override
    public final Pulled getPulled() {
        Pulled pulled = pull(currentPulled);

        if (checkPulled(Objects.requireNonNull(pulled)) && !currentPulled.equals(pulled)) {
            currentPulled = pulled;
            onPull(pulled);
            return pulled;
        } else {
            return currentPulled;
        }
    }

    protected void onPull(Pulled pulled) {
        onPull.accept(pulled);
    }

    abstract protected void pushInitial(Pulled initialPulled);

    abstract protected Pulled pull(Pulled currentPulled);
}
