package frc.bofalib.generic.loggable;

import frc.bofalib.util.MiscUtil;

public abstract class MockLoggableBase extends LoggableBase {
    protected MockLoggableBase(String name) {
        super(MiscUtil.mockName(name));
    }
}
