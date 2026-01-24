package frc.robot.subsystems.util.dashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A dashboard field for booleans.
 * 
 * @see DashboardField
 * @see BaseDashboardField
 * @see DashboardValue
 * @see #DashboardDouble(String, String, Double, FieldMode)
 */
public class DashboardDouble extends DashboardValue<Double> {
    /**
     * A pushing or pulling dashboard field for doubles.
     * 
     * @param subsystemName the subsystem name.
     * @param fieldName     the name of the field.
     * @param initialValue  the field's initial value.
     * @param fieldMode     whether the field is pushing or pulling.
     * @see DashboardValue#DashboardValue(String, String, Double, FieldMode)
     */
    public DashboardDouble(String subsystemName, String fieldName, double initialValue, FieldMode fieldMode) {
        super(subsystemName, fieldName, initialValue, fieldMode);
    }

    /**
     * {@inheritDoc}
     * 
     * @see #getInnerValue()
     * @see SmartDashboard#putNumber(String, Double)
     */
    @Override
    protected final void pushWithKey(String key) {
        SmartDashboard.putNumber(key, getInnerValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see #setInnerValue(Double)
     * @see #getDefaultValue()
     * @see SmartDashboard#getNumber(String, Double)
     */
    @Override
    protected final void pullWithKey(String key) {
        setInnerValue(SmartDashboard.getNumber(key, getDefaultValue()));
    }
}
