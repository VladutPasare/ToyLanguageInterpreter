package model.types;

import model.values.StringValue;
import model.values.Value;

public class StringType implements Type {

    public boolean equals(Object another) {
        return another instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue();
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
