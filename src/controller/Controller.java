package controller;

import model.ADTs.*;
import model.ProgramState;
import model.exceptions.*;
import model.statements.*;
import model.values.ReferenceValue;
import model.values.Value;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repository;
    private ExecutorService executor;

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

    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramStatesList());


        while(!programStates.isEmpty()) {
            garbageCollector(programStates);
            programStates = removeCompletedPrograms(repository.getProgramStatesList());
            programStates = removeDuplicateStates(programStates);
            oneStepForAllPrograms(programStates);
        }

        executor.shutdownNow();
        repository.setProgramStatesList(programStates);
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symbolsTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream().filter(e->symbolsTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void garbageCollector(List<ProgramState> programStates) {
        List<Integer> addresses = Objects.requireNonNull(programStates.stream()
                .map(p -> getAddressesFromSymbolsTable(p.getSymbolsTable().getContent().values(), p.getHeap().getContent().values())).
                map(Collection::stream).reduce(Stream::concat).orElse(null).collect(Collectors.toList()));

        programStates.forEach(programState -> {
            programState.getHeap().setContent(
                    unsafeGarbageCollector(
                            addresses,
                            programStates.get(0).getHeap().getContent()));
        });
    }

    private List<Integer> getAddressesFromSymbolsTable(Collection<Value> symbolsTableValue, Collection<Value> heap) {
        return Stream.concat(
                heap.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {
                    ReferenceValue v1 = (ReferenceValue) v;
                    return v1.getAddress();
                }),
                symbolsTableValue.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {
                            ReferenceValue v1 = (ReferenceValue) v;
                            return v1.getAddress();
                        })).collect(Collectors.toList());
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList) {
        return inProgramList.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public List<ProgramState> removeDuplicateStates(List<ProgramState> programStates) {
        return programStates.stream().distinct().collect(Collectors.toList());
    }
    void oneStepForAllPrograms(List<ProgramState> programStates) throws MyException {
        programStates.forEach(
                program -> {
                    try {
                        repository.logProgramStateExecute(program);
                    } catch (MyException e) {
                        e.printStackTrace();
                    }
                });
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .collect(Collectors.toList());

        try {
            List<ProgramState> newProgramStates = executor.invokeAll(callList).stream().
                    map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException exception) {
                            System.out.println(exception.getMessage());
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
            programStates.addAll(newProgramStates);
        } catch (InterruptedException exception) {
            throw new MyException(exception.getMessage());
        }

        programStates.forEach(program -> {
            try {
                repository.logProgramStateExecute(program);
            } catch (MyException exception) {
                exception.printStackTrace();
            }
        });
        repository.setProgramStatesList(programStates);
    }
}
