package model.expressions;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression {
    Expression e1;
    Expression e2;
    // 1 <, 2 <=, 3 ==, 4 !=, 5 >, 6 >=
    int operator;

    public RelationalExpression(Expression e1, Expression e2, int operator) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IMyDictionary<String, Value> table, IMyHeap<Integer, Value> heap) throws ExpressionException {

        if(operator < 1 || operator > 6)
            throw new ExpressionException("Operator is not valid!");

        Value v1 = e1.evaluate(table, heap);

        if(!(v1 instanceof IntValue))
            throw new ExpressionException("The expression's value couldn't be evaluated to an int value");

        Value v2 = e2.evaluate(table, heap);

        if(!(v2 instanceof IntValue))
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
                op = "<";
                break;
            case 2:
                op = "<=";
                break;
            case 3:
                op = "==";
                break;
            case 4:
                op = "!=";
                break;
            case 5:
                op = ">";
                break;
            case 6:
                op = ">=";
                break;
        }
        return e1.toString() + op + e2.toString();
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(e1.deepCopy(), e2.deepCopy(), operator);
    }

    @Override
    public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws ExpressionException {
        Type type1 = e1.typeCheck(typeEnv);
        Type type2 = e2.typeCheck(typeEnv);

        if(type1.equals(new IntType()))
            if(type2.equals(new IntType()))
                return new BoolType();
            else
                throw new ExpressionException("Second operand is not an integer!");
        throw new ExpressionException("First operand is not an integer!");
    }
}
