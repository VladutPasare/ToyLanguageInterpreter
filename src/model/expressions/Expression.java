package model.expressions;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.exceptions.ExpressionException;
import model.values.Value;

public interface Expression {
    Value evaluate(IMyDictionary<String, Value> table, IMyHeap<Integer, Value> heap) throws ExpressionException;
    Expression deepCopy();
}
