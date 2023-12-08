package model.statements;

import model.ADTs.IMyDictionary;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.StatementException;
import model.types.Type;
import model.values.Value;

public class VariableDeclarationStatement implements Statement {
    private final Type type;
    private final String name;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ADTException {
        IMyDictionary<String, Value> symbolsTable = state.getSymbolsTable();

        if(symbolsTable.isDefined(name))
            throw new StatementException("Variable " + name + " is already declared!");
        symbolsTable.put(name, type.defaultValue());
        return state;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public Statement deepCopy() {
        return new VariableDeclarationStatement(new String(name), type.deepCopy());
    }
}
