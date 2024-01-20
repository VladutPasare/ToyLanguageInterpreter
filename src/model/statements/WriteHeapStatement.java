package model.statements;

import model.ADTs.IMyDictionary;
import model.ADTs.IMyHeap;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.types.ReferenceType;
import model.types.Type;
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
        IMyDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        IMyHeap<Integer, Value> heap = state.getHeap();

        if(!symbolsTable.isDefined(id))
            throw new ExpressionException("Variable " + id + " is not declared!");

        if(!(symbolsTable.lookUp(id).getType() instanceof ReferenceType))
            throw new ExpressionException("Variable " + id + "is not a reference!");

        ReferenceValue referenceValue = (ReferenceValue) symbolsTable.lookUp(id);
        Integer address = referenceValue.getAddress();

        if(!heap.contains(address))
            throw new ExpressionException("Address " + address + " is not in the heap!");

        Value value = expression.evaluate(symbolsTable, heap);

        if(!value.getType().equals(referenceValue.getLocationType()))
            throw new ExpressionException("Expression is not of type" + referenceValue.getLocationType());

        heap.update(address, value);
        return null;
    }

    @Override
    public String toString() {
        return "WriteHeap(" + id + "," + expression + ")";
    }

    @Override
    public Statement deepCopy() {
        return null;
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        if(typeEnv.isDefined(id)) {
            Type idType = typeEnv.lookUp(id);
            Type expType = expression.typeCheck(typeEnv);

            if(idType instanceof ReferenceType) {
                if(idType.equals(new ReferenceType(expType)))
                    return typeEnv;
                else
                    throw new StatementException("The right hand side has other type than the referenced type of the left hand side!");
            }
            else
                throw new StatementException(id + " is not a reference!");
        }
        throw new StatementException(id + " is not defined!");
    }
}
