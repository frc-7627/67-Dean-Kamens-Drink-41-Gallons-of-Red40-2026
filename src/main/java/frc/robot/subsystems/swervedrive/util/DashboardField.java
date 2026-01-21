package frc.robot.subsystems.swervedrive.util;

import java.util.List;

/**
 * A class that can send and receive it's instances to and from the dashboard.
 */
public interface DashboardField<Inner> {
    /**
     * @return The inner value
     */
    Inner getInner();

    void setInner(Inner inner);

    void push();

    void pull();

    default void update() {
        push();
        pull();
    }

    public static void updateAll(List<DashboardField<?>> fields) {
        for (DashboardField<?> field : fields) {
            field.update();
        }
    }
}
