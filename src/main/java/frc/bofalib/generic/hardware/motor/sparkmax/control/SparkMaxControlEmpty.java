package frc.bofalib.generic.hardware.motor.sparkmax.control;

public final class SparkMaxControlEmpty implements SparkMaxControl {
    private static final SparkMaxControlEmpty INSTANCE = new SparkMaxControlEmpty();

    public static SparkMaxControlEmpty getInstance() {
        return INSTANCE;
    }

    private SparkMaxControlEmpty() {}

    @Override
    public String getLoggableName() {
        return "Empty Spark Max Control";
    }
}
