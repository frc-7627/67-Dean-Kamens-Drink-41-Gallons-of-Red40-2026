package frc.robot.subsystems.vision;

public final class StandardDeviations {
    private double singleTagStdDev; 
    private double multiTagStdDev;

    StandardDeviations() {
        this(0.0, 0.0);
    }

    StandardDeviations(double singleTagStdDev, double multiTagStdDev) {
        this.singleTagStdDev = singleTagStdDev;
        this.multiTagStdDev = multiTagStdDev;
    }

    void setSingleTagStdDev(double singleTagStdDev) {
        this.singleTagStdDev = singleTagStdDev;
    }

    void setMultiTagStdDev(double multiTagStdDev) {
        this.multiTagStdDev = multiTagStdDev;
    }

    double getSingleTagStdDev() {
        return singleTagStdDev;
    }

    double getMultiTagStdDev() {
        return multiTagStdDev;
    }
}
