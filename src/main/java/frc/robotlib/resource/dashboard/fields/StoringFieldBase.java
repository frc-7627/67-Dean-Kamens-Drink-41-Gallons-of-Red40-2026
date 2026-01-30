package frc.robotlib.resource.dashboard.fields;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import frc.robotlib.resource.dashboard.StoringField;

abstract class StoringFieldBase<Stored> extends SubdashboardBase implements StoringField<Stored> {
    private final Predicate<Stored> checkStored;
    private final Consumer<Stored> onStore;
    private Stored currentStored;
    
    protected StoringFieldBase(String superdashboardName, String fieldName,
            Predicate<Stored> checkStored, Consumer<Stored> onStore, Stored initialStored) {
        super(superdashboardName, fieldName);

        this.checkStored = Objects.requireNonNull(checkStored);
        this.onStore = Objects.requireNonNull(onStore);

        if (checkPushed(Objects.requireNonNull(initialStored))) {
            this.currentStored = initialStored;
            onStore(initialStored);
            push(initialStored);
        } else {
            throw new BadInitialValueError(initialStored);
        }
    }

    @Override
    public final boolean checkStored(Stored stored) {
        return checkStored.test(stored);
    }

    @Override
    public final Stored getPulled() {
        Stored stored = pull(currentStored);

        if (checkPulled(Objects.requireNonNull(stored)) && !currentStored.equals(stored)) {
            currentStored = stored;
            onStore(stored);
            return stored;
        } else {
            return currentStored;
        }
    }

    @Override
    public void setPushed(Stored stored) {
        if (checkPushed(Objects.requireNonNull(stored)) && !currentStored.equals(stored)) {
            currentStored = stored;
            onStore(stored);
            push(stored);
        }
    }

    protected void onStore(Stored stored) {
        onStore.accept(stored);
    }

    abstract protected void push(Stored stored);

    abstract protected Stored pull(Stored pulled);
}
