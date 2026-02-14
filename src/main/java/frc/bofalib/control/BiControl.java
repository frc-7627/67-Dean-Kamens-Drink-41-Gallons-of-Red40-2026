package frc.bofalib.control;

public interface BiControl<
    FirstControl, 
    SecondControl
> extends UniControl<FirstControl> {
    SecondControl getSecondControl();
}
