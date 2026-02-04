package frc.robotlib.dashboard.fields;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import frc.robotlib.dashboard.StoringField;

abstract class StoringFieldBase<Stored> extends SubdashboardBase implements StoringField<Stored> {
    private final Transport<Stored> transport;
    private final Predicate<Stored> checkStored;
    private final Consumer<Stored> onStore;
    private Stored currentStored;

    protected StoringFieldBase(String superdashboardName, String fieldName,
            Function<String, Transport<Stored>> transportConstructor, Predicate<Stored> checkStored,
            Consumer<Stored> onStore, Stored initialStored) {
        super(superdashboardName, fieldName);

        this.transport = transportConstructor.apply(getKeyName());
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

    private void push(Stored stored) {
        transport.push(stored);
    }

    private Stored pull(Stored currentStored) {
        return transport.pull(currentStored);
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
            push(currentStored);
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
}
