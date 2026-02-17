package frc.bofalib.generic.control;

public final class DefaultableControlBox<Control> {
    private final Control emptyControl;
    private Control control;

    public DefaultableControlBox(Control emptyControl) {
        this.emptyControl = emptyControl;
        this.control = emptyControl;
    }

    public Control getControl() {
        return control;
    }

    public Control getEmptyControl() {
        return emptyControl;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public void resetControl() {
        this.control = emptyControl;
    }
}
