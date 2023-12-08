package model.values;
import model.types.IntType;
import model.types.Type;

public class IntValue implements Value {
    private int value;

    public IntValue() {

    }
    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object another) {
        if(!(another instanceof IntValue))
            return false;
        int anotherValue = ((IntValue) another).getValue();
        return anotherValue == value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
