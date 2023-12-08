package model.expressions;

import model.ADTs.IMyDictionary;
import model.exceptions.ExpressionException;
import model.values.Value;

public interface Expression {
    public Value evaluate(IMyDictionary<String, Value> table) throws ExpressionException;
    public Expression deepCopy();
}
