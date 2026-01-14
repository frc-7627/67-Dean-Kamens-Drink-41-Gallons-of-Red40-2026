package frc.robot.commands.Led;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Bluetooth;

public class Rainbow extends WaitCommand{
    private final Bluetooth led;
 
    public Rainbow(Bluetooth led, double time) {
        super(time);
        this.led = led;
    }

    @Override
    public void initialize() {
        led.rainbow();
        super.initialize();
    }

    @Override
    public void end(boolean interrupted) {
        led.bluetoothOFF();
        super.end(interrupted);
    }
}
