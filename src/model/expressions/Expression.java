package model.expressions;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.ADTs.MyDictionary;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.types.Type;
import model.values.Value;

public interface Expression {
    Value evaluate(IMyDictionary<String, Value> table, IMyHeap<Integer, Value> heap) throws ExpressionException;
    Expression deepCopy();
    Type typeCheck(IMyDictionary<String, Type> typeEnv) throws ExpressionException;
}
