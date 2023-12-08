package model.types;

import model.values.Value;

public interface Type {
    public Value defaultValue();
    public Type deepCopy();
}
