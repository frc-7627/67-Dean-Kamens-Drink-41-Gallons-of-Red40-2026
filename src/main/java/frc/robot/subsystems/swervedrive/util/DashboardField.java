package frc.robot.subsystems.swervedrive.util;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A subsystem field that can push and pull values to and from the dashboard.
 */
public class DashboardField<Subsystem, Value extends DashboardValue<Value>> {
    private final String name;
    private final Function<Subsystem, Value> getter;
    private final Consumer<Pair<Subsystem, Value>> setter;
    private final Value defaultValue;
    private final boolean hasSetter;
    private final boolean isConstant;

    /**
     * Create a read-only field with the provided getter.
     * 
     * @param name the name of the field.
     * @param getter the provided getter.
     */
    public DashboardField(String name, Function<Subsystem, Value> getter) {
        this.name = name;
        this.getter = getter;
        this.setter = null;
        this.defaultValue = null;
        this.hasSetter = false;
        this.isConstant = true;
    }

    /**
     * Create a read-write field with the provided getter, setter, and default value.
     * 
     * @param name the name of the field.
     * @param getter the provided getter.
     * @param setter the provided setter.
     * @param defaultValue the provided default value.
     * @param isConstant whether the field can only be set once.
     */
    public DashboardField(String name, Function<Subsystem, Value> getter,
            Consumer<Pair<Subsystem, Value>> setter, Value defaultValue, boolean isConstant) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.defaultValue = defaultValue;
        this.hasSetter = true;
        this.isConstant = isConstant;
    }

    /**
     * Get the key for pushing from the subsystem name.
     * 
     * @param subsystemName the subsystem name.
     * @return the key for pushing.
     */
    private String getPushKey(String subsystemName) {
        return String.format("{0}/{1}", subsystemName, name);
    }

    /**
     * Get the key for pulling from the subsystem name.
     * 
     * @param subsystemName the subsystem name.
     * @return the key for pulling.
     */
    private String getPullKey(String subsystemName) {
        if (isConstant) {
            return String.format("{0}/const/{1}", subsystemName, name);
        } else {
            return getPushKey(subsystemName);
        }
    }

    /**
     * Push the subsystem field's value to the dashboard.
     * 
     * Applies the getter to the subsystem and sends the value.
     * 
     * @param subsystem the subsystem.
     */
    private void push(Subsystem subsystem) {
        getter.apply(subsystem).send(getPushKey(subsystem.getClass().getSimpleName()));
    }

    /**
     * Pull the subsystem field's value from the dashboard.
     * 
     * If there is a setter, receive a value from the dashboard, falling back on the default value,
     * and apply the setter on the subsystem and received value.
     * 
     * @param subsystem the subsystem.
     */
    private void pull(Subsystem subsystem) {
        if (hasSetter) {
            setter.accept(new Pair<Subsystem, Value>(subsystem,
                    defaultValue.recv(getPullKey(subsystem.getClass().getSimpleName()))));
        }
    }

    /**
     * Update the field of the subsystem.
     * 
     * Pushes and pulls the subsystem's value to and from the dashboard.
     * 
     * @param subsystem the subsystem.
     */
    private void update(Subsystem subsystem) {
        push(subsystem);
        pull(subsystem);
    }

    /**
     * Update all provided fields of the subsystem.
     * 
     * @param <Subsystem> a subsystem.
     * @param fields all provided fields.
     * @param subsystem the subsystem.
     */
    public static <Subsystem> void updateAll(List<DashboardField<Subsystem, ?>> fields,
            Subsystem subsystem) {
        for (DashboardField<Subsystem, ?> field : fields) {
            field.update(subsystem);
        }
    }
}
