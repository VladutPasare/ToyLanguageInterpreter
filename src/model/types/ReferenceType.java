package model.types;

import model.values.ReferenceValue;
import model.values.Value;

public class ReferenceType implements Type {
    private Type inner;
    public ReferenceType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another) {
        if(another instanceof ReferenceType)
            return ((ReferenceType) another).inner.equals(inner);
        return false;
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(0, inner);
    }

    @Override
    public Type deepCopy() {
        return new ReferenceType(inner.deepCopy());
    }

    public String toString() {
        return "Ref(" + inner + ")";
    }

}
