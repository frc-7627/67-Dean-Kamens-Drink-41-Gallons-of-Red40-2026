package frc.bofalib.control;

public interface BiControl<
    Target,
    FirstControl, 
    SecondControl
> extends UniControl<Target, FirstControl> {
    SecondControl getSecondControl(Target target);
}
