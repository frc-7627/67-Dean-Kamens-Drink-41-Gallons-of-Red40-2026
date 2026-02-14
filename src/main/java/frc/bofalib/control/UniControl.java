package frc.bofalib.control;

public interface UniControl<Target, FirstControl> {
    FirstControl getFirstControl(Target target);
}
