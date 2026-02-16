package frc.bofalib.generic.control;

public interface UniControl<Target, FirstControl> {
    FirstControl getFirstControl(Target target);
}
