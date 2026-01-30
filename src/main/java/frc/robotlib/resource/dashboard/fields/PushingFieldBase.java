package frc.robotlib.resource.dashboard.fields;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import frc.robotlib.resource.dashboard.PushingField;

abstract class PushingFieldBase<Pushed> extends SubdashboardBase
        implements PushingField<Pushed> {
    private final Transport<Pushed> transport;
    private final Predicate<Pushed> checkPushed;
    private final Consumer<Pushed> onPush;
    private Pushed currentPushed;

    protected PushingFieldBase(String superdashboardName, String fieldName,
            Function<String, Transport<Pushed>> transportConstructor,
            Predicate<Pushed> checkPushed, Consumer<Pushed> onPush, Pushed initialPushed) {
        super(superdashboardName, superdashboardName);

        this.transport = transportConstructor.apply(getKeyName());
        this.checkPushed = Objects.requireNonNull(checkPushed);
        this.onPush = Objects.requireNonNull(onPush);

        if (checkPushed(Objects.requireNonNull(initialPushed))) {
            this.currentPushed = initialPushed;
            onPush(initialPushed);
            push(initialPushed);
        } else {
            throw new BadInitialValueError(initialPushed);
        }
    }

    private void push(Pushed pushed) {
        transport.push(pushed);
    }

    @Override
    public final boolean checkPushed(Pushed pushed) {
        return checkPushed.test(pushed);
    }

    @Override
    public final void setPushed(Pushed pushed) {
        if (checkPushed(Objects.requireNonNull(pushed)) && !currentPushed.equals(pushed)) {
            this.currentPushed = pushed;
            onPush(pushed);
            push(pushed);
        }
    }

    protected void onPush(Pushed pushed) {
        onPush.accept(pushed);
    }
}
