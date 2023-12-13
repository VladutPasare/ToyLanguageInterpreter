package model.statements;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.values.ReferenceValue;
import model.values.Value;

public class WriteHeapStatement implements Statement {
    String id;
    Expression expression;

    public WriteHeapStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTException {
        IMyDictionary<String, Value> table = state.getSymbolsTable();
        IMyHeap<Integer, Value> heap = state.getHeap();

        if(!table.isDefined(id))
            throw new ExpressionException("Variable " + id + " is not declared!");

        if(!(table.lookUp(id) instanceof ReferenceValue))
            throw new ExpressionException("Variable " + id + "is not a reference!");

        ReferenceValue referenceValue = (ReferenceValue) table.lookUp(id);
        Integer address = referenceValue.getAddress();

        if(!heap.contains(address))
            throw new ExpressionException("Address " + address + " is not in the heap!");

        Value value = expression.evaluate(table, heap);

        if(!value.getType().equals(referenceValue.getLocationType()))
            throw new ExpressionException("Expression is not of type" + referenceValue.getLocationType());

        heap.update(address, value);
        return state;
    }

    @Override
    public String toString() {
        return "WriteHeap(" + id + "," + expression + ")";
    }

    @Override
    public Statement deepCopy() {
        return null;
    }
}
