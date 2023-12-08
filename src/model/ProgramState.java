package model;

import model.ADTs.*;
import model.statements.NopStatement;
import model.statements.Statement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ProgramState {
    private IMyStack<Statement> executionStack;
    private IMyDictionary<String, Value> symbolsTable;
    private IMyList<Value> out;
    private IMyDictionary<StringValue, BufferedReader> fileTable;

    public ProgramState(IMyStack<Statement> executionStack, IMyDictionary<String, Value> symbolsTable,
                        IMyList<Value> out) {
        this.executionStack = executionStack;
        this.symbolsTable = symbolsTable;
        this.out = out;
        this.fileTable = new MyDictionary<>();
    }

    public ProgramState(IMyStack<Statement> executionStack, IMyDictionary<String, Value> symbolsTable,
                        IMyList<Value> out, IMyDictionary<StringValue, BufferedReader> fileTable) {
        this.executionStack = executionStack;
        this.symbolsTable = symbolsTable;
        this.out = out;
        this.fileTable = fileTable;
    }

    public IMyStack<Statement> getExecutionStack() {
        return executionStack;
    }

    public IMyDictionary<String, Value> getSymbolsTable() {
        return symbolsTable;
    }

    public IMyList<Value> getOut() {
        return out;
    }

    public IMyDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setExecutionStack(IMyStack<Statement> executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolsTable(IMyDictionary<String, Value> symbolsTable) {
        this.symbolsTable = symbolsTable;
    }

    public void setOut(IMyList<Value> out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return  "Execution Stack:\n" + executionStack.toString() + "\n\n" +
                "Symbols Table:\n" + symbolsTable.toString() + "\n\n" +
                "Out:\n" + out.toString() + "\n\n" +
                "File Table:\n" + fileTable.toString() + "\n\n";
    };
}
