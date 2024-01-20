package model.expressions;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression {
    private final Expression e1, e2;
    private int operator; // 1-plus, 2-minus, 3-star, 4-divide

    public ArithmeticExpression(char operator, Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;

        if(operator == '+')
            this.operator = 1;
        if(operator == '-')
            this.operator = 2;
        if(operator == '*')
            this.operator = 3;
        if(operator == '/')
            this.operator = 4;
    }
    @Override
    public Value evaluate(IMyDictionary<String, Value> table, IMyHeap<Integer, Value> heap) throws ExpressionException {
        Value v1, v2;
        v1 = e1.evaluate(table, heap);
        if(v1 instanceof IntValue) {
            v2 = e2.evaluate(table, heap);
            if(v2 instanceof IntValue) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                switch (operator) {
                    case 1:
                        return new IntValue(n1 + n2);
                    case 2:
                        return new IntValue(n1 - n2);
                    case 3:
                        return new IntValue(n1 * n2);
                    case 4:
                        if(n2 == 0)
                            throw new ExpressionException("Division by 0!");
                        else
                            return new IntValue(n1 / n2);
                    default:
                        throw new ExpressionException("Invalid operator!");
                }
            }
            else
                throw new ExpressionException("Second operand is not an integer!");
        }
        else
            throw new ExpressionException("First operand is not an integer!");
    }
    @Override
    public String toString() {
        return switch (operator) {
            case 1 -> e1.toString() + '+' + e2.toString();
            case 2 -> e1.toString() + '-' + e2.toString();
            case 3 -> e1.toString() + '*' + e2.toString();
            case 4 -> e1.toString() + '/' + e2.toString();
            default -> "";
        };
    }

    @Override
    public Expression deepCopy() {
        return switch (operator) {
            case 1 -> new ArithmeticExpression('+', e1.deepCopy(), e2.deepCopy());
            case 2 -> new ArithmeticExpression('-', e1.deepCopy(), e2.deepCopy());
            case 3 -> new ArithmeticExpression('*', e1.deepCopy(), e2.deepCopy());
            case 4 -> new ArithmeticExpression('/', e1.deepCopy(), e2);
            default -> new ArithmeticExpression(' ', e1.deepCopy(), e2.deepCopy());
        };
    }

    @Override
    public Type typeCheck(IMyDictionary<String, Type> typeEnv) throws ExpressionException {
        Type type1 = e1.typeCheck(typeEnv);
        Type type2 = e2.typeCheck(typeEnv);

        if(type1.equals(new IntType()))
            if(type2.equals(new IntType())) {
                return new IntType();
            }
            else
                throw new ExpressionException("Second operand is not an integer!");
       throw new ExpressionException("First operand is not an integer!");
    }
}
