package model.statements;

import model.ADTs.IMyList;
import model.ProgramState;
import model.exceptions.ExpressionException;
import model.expressions.Expression;
import model.values.Value;

public class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException {
        IMyList<Value> out = state.getOut();
        out.add(expression.evaluate(state.getSymbolsTable(), state.getHeap()));
        return state;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
    @Override
    public Statement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }
}
