package model.expressions;

import model.ADTs.IMyDictionary;
import model.exceptions.ExpressionException;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression {
    Expression e1;
    Expression e2;
    // 1 <, 2 <=, 3 ==, 4 !=, 5 >, 5 >=
    int operator;

    public RelationalExpression(Expression e1, Expression e2, int operator) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> table) throws ExpressionException {
        Value v1 = e1.evaluate(table);

        if(!v1.getType().equals(new IntType()))
            throw new ExpressionException("The expression's value couldn't be evaluated to an int value");

        Value v2 = e2.evaluate(table);

        if(!v2.getType().equals(new IntType()))
            throw new ExpressionException("The expression's value couldn't be evaluated to an int value");

        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;

        switch(operator) {
            case 1:
                return new BoolValue(i1.getValue() < i2.getValue());
            case 2:
                return new BoolValue(i1.getValue() <= i2.getValue());
            case 3:
                return new BoolValue(i1.getValue() == i2.getValue());
            case 4:
                return new BoolValue(i1.getValue() != i2.getValue());
            case 5:
                return new BoolValue(i1.getValue() > i2.getValue());
            case 6:
                return new BoolValue(i1.getValue() >= i2.getValue());
            default:
                throw new ExpressionException("Invalid operator");
        }

    }

    @Override
    public String toString() {
        String op = "";

        switch (operator) {
            case 1:
                op += "<";
            case 2:
                op += "<=";
            case 3:
                op += "==";
            case 4:
                op += "!=";
            case 5:
                op += ">";
            case 6:
                op += ">=";
        }
        return e1.toString() + op + e2.toString();
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(e1.deepCopy(), e2.deepCopy(), operator);
    }
}
