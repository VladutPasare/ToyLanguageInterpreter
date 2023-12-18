package model;

import model.ADTs.*;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.statements.Statement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ProgramState {
    private IMyStack<Statement> executionStack;
    private IMyDictionary<String, Value> symbolsTable;
    private IMyHeap<Integer, Value> heap;
    private IMyList<Value> out;
    private IMyDictionary<StringValue, BufferedReader> fileTable;
    private int stateID;
    private static int id = 0;

    public ProgramState(IMyStack<Statement> executionStack, IMyDictionary<String, Value> symbolsTable,
                        IMyList<Value> out, IMyHeap<Integer, Value> heap) {
        this.executionStack = executionStack;
        this.symbolsTable = symbolsTable;
        this.heap = heap;
        this.out = out;
        this.fileTable = new MyDictionary<>();
    }

    public ProgramState(IMyStack<Statement> executionStack, IMyDictionary<String, Value> symbolsTable,
                        IMyList<Value> out, IMyDictionary<StringValue, BufferedReader> fileTable, IMyHeap<Integer, Value> heap) {
        this.executionStack = executionStack;
        this.symbolsTable = symbolsTable;
        this.heap = heap;
        this.out = out;
        this.fileTable = fileTable;
        this.stateID = getNewID();
    }

    public IMyStack<Statement> getExecutionStack() {
        return executionStack;
    }

    public IMyDictionary<String, Value> getSymbolsTable() {
        return symbolsTable;
    }

    public IMyHeap<Integer, Value> getHeap() {
        return heap;
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

    public void setHeap(IMyHeap<Integer, Value> heap) {
        this.heap = heap;
    }

    public void setOut(IMyList<Value> out) {
        this.out = out;
    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStep() throws MyException, StatementException, ADTException, ExpressionException {
        if(executionStack.isEmpty())
            throw new MyException("Program state's execution stack is empty!");
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public static synchronized int  getNewID() {
        return ++id;
    }

    @Override
    public String toString() {
        return  "ID: " + stateID + "\n\n" +
                "Execution Stack:\n" + executionStack + "\n\n" +
                "Symbols Table:\n" + symbolsTable + "\n\n" +
                "Heap:\n" + heap + "\n\n" +
                "Out:\n" + out + "\n\n" +
                "File Table:\n" + fileTable + "\n\n";
    };
}
