package model.statements;

import model.ADTs.IMyDictionary;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class NewStatement implements Statement {
    private final String id;
    private final Expression expression;

    public NewStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTException {
        IMyDictionary<String, Value> symbolTable = state.getSymbolsTable();

        if(!symbolTable.isDefined(id))
            throw new StatementException("The variable " + id + " is not defined!");

        if(!(symbolTable.lookUp(id) instanceof ReferenceValue))
            throw new StatementException(id + " is not a reference!");

        Value expressionValue = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        ReferenceValue idValue = (ReferenceValue) symbolTable.lookUp(id);

        if(!idValue.getLocationType().equals(expressionValue.getType())) {
            // System.out.println(idValue.getLocationType().toString() + expressionValue.getType().toString());
            throw new StatementException("Expression is not of type " + idValue.getLocationType());

        }
        Integer newFreeLocation = state.getHeap().getFreeLocation();
        int address = state.getHeap().allocate(newFreeLocation, expressionValue);
        symbolTable.update(id, new ReferenceValue(address, expressionValue.getType()));
        return null;
    }

    @Override
    public String toString() {
        return "new(" + id + "," + expression + ")";
    }

    @Override
    public Statement deepCopy() {
        return new NewStatement(new String(id), expression.deepCopy());
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        Type varType = typeEnv.lookUp(id);
        Type expType = expression.typeCheck(typeEnv);

        if(varType.equals(new ReferenceType(expType)))
            return typeEnv;
        else
            throw new StatementException("New statement: right hand side and left hand side have different types!");
    }
}
