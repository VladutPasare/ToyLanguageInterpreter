package model.statements;

import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

import java.io.*;

public class OpenRFileStatement implements Statement {
    private Expression expression;

    public OpenRFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTException {
        Value value = expression.evaluate(state.getSymbolsTable(), state.getHeap());

        if(!(value instanceof StringValue))
            throw new StatementException("The value couldn't be evaluated to a string value");

        StringValue stringValue = (StringValue) value;

        if(state.getFileTable().isDefined(stringValue))
            throw new StatementException("Key " + stringValue.getValue() + " is already in File Table");

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(stringValue.getValue()));
            state.getFileTable().update(stringValue, bufferedReader);
        }
        catch (IOException e) {
            throw new StatementException(e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "open(" + expression.toString() + ")";
    }

    @Override
    public Statement deepCopy() {
        return new OpenRFileStatement(expression.deepCopy());
    }
}
