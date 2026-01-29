package frc.robot.subsystems.drivebase;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public interface InputSupplier {

    /**
     * When given the controller joystick input and tells the robot what to do
     * 
     * @param x   the robot x value
     * @param y   the robt y value
     * @param rot the robot rotation
     * @return the value telling the robot how to move
     */
    Supplier<ChassisSpeeds> getInput(DoubleSupplier x, DoubleSupplier y, DoubleSupplier rot);
}
