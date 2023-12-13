package model.statements;

import model.ADTs.IMyDictionary;
import model.ProgramState;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.types.Type;
import model.values.Value;

public class AssignmentStatement implements Statement {
    private final String id;
    private final Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        IMyDictionary<String, Value> symbolsTable = state.getSymbolsTable();

        if(symbolsTable.isDefined(id)) {
            Value value = expression.evaluate(symbolsTable, state.getHeap());
            Type idType = symbolsTable.lookUp(id).getType();

            if(value.getType().equals(idType))
                symbolsTable.update(id, value);
            else
                throw new StatementException("Declared type of variable " + id + "and type of assigned expression do not match!");
        }
        else
            throw new StatementException("The used variable " + id + "was not declared before!");
        return state;
    }

    @Override
    public String toString() {
        return id + "=" + expression.toString();
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(new String(id), expression.deepCopy());
    }
}
