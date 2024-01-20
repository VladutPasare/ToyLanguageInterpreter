package model.statements;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyList;
import model.ProgramState;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.types.Type;
import model.values.Value;

public class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException {
        IMyList<Value> out = state.getOut();
        System.out.println(expression + "*");
        out.add(expression.evaluate(state.getSymbolsTable(), state.getHeap()));
        return null;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
    @Override
    public Statement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }
}
