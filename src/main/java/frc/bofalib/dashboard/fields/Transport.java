package frc.bofalib.dashboard.fields;

interface Transport<Value> {
    Value pull(Value currentValue);

    void push(Value value);

    static Transport<Boolean> createBoolean(String key) {
        return new TransportBoolean(key);
    }

    static Transport<Double> createDouble(String key) {
        return new TransportDouble(key);
    }
}
