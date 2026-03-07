package frc.robot.subsystems.controllable.swivel;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.bofalib.control.Controllable;
import frc.bofalib.query.BooleanQueryable;

public interface Swivel extends Subsystem, 
Controllable<SwivelControl>,
 BooleanQueryable<SwivelBooleanQuery>
{
    static Swivel create() {
        return new SwivelImpl();
    }
}
