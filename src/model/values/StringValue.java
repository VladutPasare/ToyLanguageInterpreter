package model.values;

import model.types.StringType;
import model.types.Type;

import java.util.Objects;

public class StringValue implements Value {
    private String value;

    public StringValue() {

    }

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object another) {
        if(!(another instanceof StringValue))
            return false;
        String anotherValue = ((StringValue) another).getValue();
        return anotherValue.equals(value);
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
