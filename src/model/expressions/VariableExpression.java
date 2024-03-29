package model.expressions;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.types.Type;
import model.values.Value;

public class VariableExpression implements Expression {
    private final String id;

    public VariableExpression(String id) {
        this.id = id;
    }
    @Override
    public Value evaluate(IMyDictionary<String, Value> table, IMyHeap<Integer, Value> heap) throws ExpressionException {
        return table.lookUp(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(new String(id));
    }

    @Override
    public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws ExpressionException {
        return typeEnv.lookUp(id);
    }
}
