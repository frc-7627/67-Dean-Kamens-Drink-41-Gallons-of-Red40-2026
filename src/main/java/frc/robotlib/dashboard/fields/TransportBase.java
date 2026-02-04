package frc.robotlib.dashboard.fields;

abstract class TransportBase<Value> implements Transport<Value> {
    private final String key;

    TransportBase(String key) {
        this.key = key;
    }

    protected final String getKey() {
        return key;
    }
}
