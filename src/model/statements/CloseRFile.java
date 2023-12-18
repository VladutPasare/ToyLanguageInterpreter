package model.statements;

import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements Statement {
    private Expression expression;

    public CloseRFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTException {
        Value value = expression.evaluate(state.getSymbolsTable(), state.getHeap());

        if(!(value.getType() instanceof StringType))
            throw new StatementException(value + "The value couldn't be evaluated to a string value");

        StringValue stringValue = (StringValue) value;

        if(!state.getFileTable().isDefined(stringValue))
            throw new StatementException("File " + stringValue.getValue() + " is not in File Table");

        BufferedReader bufferedReader = state.getFileTable().lookUp(stringValue);
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new StatementException(e.getMessage());
        }
        state.getFileTable().remove(stringValue);
        return null;
    }

    @Override
    public String toString() {
        return "close(" + expression + ")";
    }

    @Override
    public Statement deepCopy() {
        return new CloseRFile(expression.deepCopy());
    }
}
