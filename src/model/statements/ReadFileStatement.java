package model.statements;

import model.ADTs.IMyDictionary;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.types.IntType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement {
    private Expression expression;
    private String variableName;

    public ReadFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTException {
        IMyDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if(!symbolsTable.isDefined(variableName))
           throw new StatementException("Variable " + variableName + " not defined");

        if(!symbolsTable.lookUp(variableName).getType().equals(new IntType()))
            throw new StatementException(variableName + " is not of type int");

        Value value = expression.evaluate(symbolsTable);

        if(!value.getType().equals(new StringType()))
            throw new StatementException(value + "The value couldn't be evaluated to a string value");

        StringValue stringValue = (StringValue) value;

        if(!fileTable.isDefined(stringValue))
            throw new StatementException("The file " + stringValue + " is not defined");

        BufferedReader bufferedReader = fileTable.lookUp(stringValue);

        try {
            String line = bufferedReader.readLine();

            if(line.isEmpty())
                throw new StatementException("Empty line");

            else {
                symbolsTable.update(variableName, new IntValue(Integer.parseInt(line))); }

        } catch (IOException e) {
            throw new StatementException(e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "read(" + expression + ", " + variableName + ")";
    }

    @Override
    public Statement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), new String(variableName));
    }
}
