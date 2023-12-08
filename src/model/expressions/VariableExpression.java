package model.expressions;

import model.ADTs.IMyDictionary;
import model.exceptions.ExpressionException;
import model.values.Value;

public class VariableExpression implements Expression {
    private final String id;

    public VariableExpression(String id) {
        this.id = id;
    }
    @Override
    public Value evaluate(IMyDictionary<String, Value> table) throws ExpressionException {
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
}
