package controller;

import model.ADTs.*;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.statements.*;
import model.values.ReferenceValue;
import model.values.Value;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void addProgram(ProgramState programState) {
        repository.add(programState);
    }

    public ProgramState oneStep(ProgramState programState) throws MyException, StatementException, ADTException, ExpressionException {
        IMyStack<Statement> executionStack = programState.getExecutionStack();

        if(executionStack.isEmpty())
            throw new MyException("Program state's execution stack is empty!");
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(programState);
    }

    public void allSteps() throws Exception {
        ProgramState programState = repository.getCurrentProgram();
        System.out.println("\u001B[36m" + programState);
        repository.logProgramStateExecute(programState);

        while(!programState.getExecutionStack().isEmpty()) {
            try {
                oneStep(programState);
                System.out.println(programState); // flag
                repository.logProgramStateExecute(programState);

                programState.getHeap().setContent(unsafeGarbageCollector(getAddressesFromSymbolsTable(
                        programState.getSymbolsTable().getContent().values()), programState.getHeap().getContent()));
            }
            catch (ADTException | ExpressionException | StatementException | MyException exception) {
                throw new MyException(exception.getMessage());
            }
        }
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symbolsTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream().filter(e->symbolsTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddressesFromSymbolsTable(Collection<Value> symbolsTableValue) {
        return symbolsTableValue.stream().filter(v->v instanceof ReferenceValue).map(
                v-> { ReferenceValue v1 = (ReferenceValue) v;
                    return v1.getAddress();}
        ).collect(Collectors.toList());
    }
}
