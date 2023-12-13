package model.expressions;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.exceptions.ExpressionException;
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
        return null;
    }
}
