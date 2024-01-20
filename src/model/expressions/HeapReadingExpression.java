package model.expressions;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class HeapReadingExpression implements Expression {
    Expression expression;

    public HeapReadingExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> table, IMyHeap<Integer, Value> heap) throws ExpressionException {
        Value value = expression.evaluate(table, heap);

        if(!(value instanceof ReferenceValue))
            throw new ExpressionException("Expression couldn't be evaluated to reference type");

        ReferenceValue referenceValue = (ReferenceValue) value;
        Integer address = referenceValue.getAddress();

        if(!heap.contains(address))
            throw new ExpressionException("Address " + address + " is not in the heap!");

        return heap.getValue(address);

    }

    @Override
    public String toString() {
        return "ReadHeap(" + expression.toString() + ")";
    }
    @Override
    public Expression deepCopy() {
        return new HeapReadingExpression(expression.deepCopy());
    }

    @Override
    public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws ExpressionException {
        Type type = expression.typeCheck(typeEnv);
        if(type instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInner();
        }
        else throw new ExpressionException("The expression argument is not a reference type!");
    }
}
