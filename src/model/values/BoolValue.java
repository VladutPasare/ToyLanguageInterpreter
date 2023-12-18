package model.values;

import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value {
    private boolean value;

    public BoolValue() {

    }

    public BoolValue(boolean value) {
        this.value = value;
    }


    public boolean equals(BoolValue another) {
        return value == another.value;
    }

    @Override
    public boolean equals(Object another) {
        if(!(another instanceof BoolValue))
            return false;
        boolean anotherValue = ((BoolValue) another).value;
        return anotherValue == value;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(value);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
