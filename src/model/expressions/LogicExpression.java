package model.expressions;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.exceptions.ExpressionException;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;

public class LogicExpression implements Expression {
    private final Expression e1, e2;
    int operator;

    public LogicExpression(Expression e1, Expression e2, String operator) {
        this.e1 = e1;
        this.e2 = e2;

        if(operator.equals("and"))
            this.operator = 1;
        if(operator.equals("or"))
            this.operator = 2;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> table, IMyHeap<Integer, Value> heap) throws ExpressionException {
        Value v1, v2;
        v1 = e1.evaluate(table, heap);

        if(v1 instanceof  BoolValue) {
            v2 = e2.evaluate(table, heap);
            if(v2 instanceof BoolValue) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = b1.getValue();
                n2 = b2.getValue();

                if(operator == 1)
                        return new BoolValue(n1 && n2);

                else if(operator == 2)
                            return new BoolValue(n1 || n2);
                    else
                        throw new ExpressionException("Invalid operator!");
            }
            else
                throw new ExpressionException("Second operand is not a boolean!");
        }
        else throw new ExpressionException("First operand is not a boolean!");
    }

    @Override
    public String toString() {
        return switch (operator) {
            case 1 -> e1.toString() + " and " + e2.toString();
            case 2 -> e1.toString() + " or " + e2.toString();
            default -> "";
        };
    }

    @Override
    public Expression deepCopy() {
        if(operator == 1)
            return new LogicExpression(e1.deepCopy(), e2.deepCopy(), "and");
        if(operator == 2)
            return new LogicExpression(e1.deepCopy(), e2.deepCopy(), "or");
        return null;
    }
}
