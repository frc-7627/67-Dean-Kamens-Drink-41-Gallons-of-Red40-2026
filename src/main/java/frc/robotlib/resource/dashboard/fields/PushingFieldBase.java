package frc.robotlib.resource.dashboard.fields;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import frc.robotlib.resource.dashboard.PushingField;

abstract class PushingFieldBase<Pulled, Pushed> extends SubdashboardBase
        implements PushingField<Pulled, Pushed> {
    private final Predicate<Pushed> checkPushed;
    private final Consumer<Pushed> onPush;

    protected PushingFieldBase(String superdashboardName, String fieldName,
            Predicate<Pushed> checkPushed, Consumer<Pushed> onPush, Pushed initialPushed) {
        super(superdashboardName, superdashboardName);

        this.checkPushed = Objects.requireNonNull(checkPushed);
        this.onPush = Objects.requireNonNull(onPush);

        if (checkPushed(Objects.requireNonNull(initialPushed))) {
            onPush(initialPushed);
            push(initialPushed);
        } else {
            throw new BadInitialValueError(initialPushed);
        }
    }

    @Override
    public final boolean checkPushed(Pushed pushed) {
        return checkPushed.test(pushed);
    }

    @Override
    public final void setPushed(Pushed pushed) {
        if (checkPushed(Objects.requireNonNull(pushed))) {
            onPush(pushed);
            push(pushed);
        }
    }

    protected void onPush(Pushed pushed) {
        onPush.accept(pushed);
    }

    abstract protected void push(Pushed pushed);
}
