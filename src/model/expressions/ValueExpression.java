package model.expressions;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.types.Type;
import model.values.Value;

public class ValueExpression implements Expression {
    private final Value e;

    public ValueExpression(Value e) {
        this.e = e;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> table, IMyHeap<Integer, Value> heap) throws ExpressionException {
        return e;
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(e);
    }

    @Override
    public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws ExpressionException {
        return e.getType();
    }
}
