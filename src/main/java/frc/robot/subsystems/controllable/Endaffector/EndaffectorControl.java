package frc.robot.subsystems.controllable.Endaffector;

public enum EndaffectorControl implements UniControl<AgitatorImpl, SparkMaxControl>, Loggable {

    OUT("Eject Out", impl -> impl.dutyCycleSupplier),
    OUT_MANUAL("Eject Out Manual", impl -> impl.manualDutyCycleSupplier),
    IN("Agitate Toward", impl -> FunctionalUtil.negativeSupplier(
        impl.dutyCycleSupplier
    )),
    IN_MANUAL("In Manual", impl -> FunctionalUtil.negativeSupplier(
        impl.manualDutyCycleSupplier
    ));

    private final String name;
    private final Function<EndaffectorImpl, SparkMaxControl> firstControlFunction;

    EndaffectorControl(String name, Function<EndaffectorImpl, DoubleSupplier> dutyCycleFunction) {
        this.name = name;
        this.firstControlFunction = impl -> impl.motor.getSetDutyCycleControl(
            dutyCycleFunction.apply(impl)
        );
    }

    @Override
    public String getLoggableName() {
        return name;
    }

    @Override
    public SparkMaxControl getFirstControl(AgitatorImpl agitatorImpl) {
        return firstControlFunction.apply(agitatorImpl);
    } 
}
