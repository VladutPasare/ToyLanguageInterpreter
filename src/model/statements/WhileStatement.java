package model.statements;


import model.ADTs.IMyDictionary;
import model.ADTs.IMyStack;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStatement implements Statement {
    Expression expression;
    Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTException {
        IMyStack<Statement> stack = state.getExecutionStack();

        Value value = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        if(!(value instanceof BoolValue))
            throw new StatementException("Expression is not a boolean!");

        if(((BoolValue) value).equals(new BoolValue(true))) {
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public String toString() {
        return "(while(" + expression + ")" + statement + ")";
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type expType = expression.typeCheck(typeEnv);

        if(expType.equals(new BoolType())) {
            statement.typeCheck(typeEnv);
            return typeEnv;
        }
        else
            throw new StatementException("Expression has not the type boolean!");
    }
}
