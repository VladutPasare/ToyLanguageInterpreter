package model.statements;

import model.ADTs.IMyDictionary;
import model.ADTs.MyDictionary;
import model.ADTs.MyStack;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.types.Type;
import model.values.Value;

import java.util.Map;

public class ForkStatement implements Statement {
    Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTException {
        IMyDictionary<String, Value> symbolsTable = state.getSymbolsTable();
        MyStack<Statement> newExecutionStack = new MyStack<>();
        newExecutionStack.push(statement);
        MyDictionary<String, Value> newSymbolsTable = new MyDictionary<>();

        for(Map.Entry<String, Value> entry: symbolsTable.getContent().entrySet()) {
            newSymbolsTable.update(new String(entry.getKey()), entry.getValue().deepCopy());
        }

        return new ProgramState(newExecutionStack, newSymbolsTable, state.getOut(), state.getFileTable(), state.getHeap());
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        statement.typeCheck(typeEnv);
        return typeEnv;
    }
}
