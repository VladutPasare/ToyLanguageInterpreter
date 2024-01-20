package model.statements;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyStack;
import model.ProgramState;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class IfStatement implements Statement {
    private final Expression expression;
    private final Statement thenStatement;
    private final Statement elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        IMyStack<Statement> executionStack = state.getExecutionStack();
        Value condition = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        if(!(condition.getType() instanceof BoolType))
            throw new StatementException("Condition is not boolean!");

        BoolValue c = (BoolValue) condition;
        if(c.getValue())
            executionStack.push(thenStatement);
        else
            executionStack.push(elseStatement);
        return null;
    }

    @Override
    public String toString() {
        return "if(" + expression.toString() + ")then(" + thenStatement.toString() + ")else(" + elseStatement.toString() + ")";
    }

    @Override
    public Statement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type expType = expression.typeCheck(typeEnv);
        if(expType.equals(new BoolType())) {
            thenStatement.typeCheck(typeEnv.deepCopy());
            elseStatement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new StatementException("The condition of IF has not the type bool!");
    }
}
