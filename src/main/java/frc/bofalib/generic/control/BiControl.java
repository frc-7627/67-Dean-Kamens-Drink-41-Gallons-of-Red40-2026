package frc.bofalib.generic.control;

public interface BiControl<
    Target,
    FirstControl, 
    SecondControl
> extends UniControl<Target, FirstControl> {
    SecondControl getSecondControl(Target target);
}
