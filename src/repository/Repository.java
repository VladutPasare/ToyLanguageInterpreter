package repository;

import model.ProgramState;
import model.exceptions.MyException;

import java.io.*;
import java.util.List;
import java.util.LinkedList;

public class Repository implements IRepository {
    private List<ProgramState> programStates;
    String logFilePath;

    public Repository(String logFilePath) throws MyException {
        this.logFilePath = logFilePath;
        try (FileWriter fileWriter = new FileWriter(logFilePath)) {

        } catch (IOException e) {
            //throw new MyException(e.getMessage());
        }

        programStates = new LinkedList<>();
    }

    @Override
    public ProgramState getCurrentProgram() {
        return programStates.getLast();
    }

    @Override
    public void add(ProgramState state) {
        programStates.add(state);
    }

    @Override
    public List<ProgramState> getProgramStatesList() {
        return programStates;
    }

    @Override
    public void setProgramStatesList(List<ProgramState> programStatesList) {
        this.programStates = programStatesList;
    }

    public ProgramState getProgramState() {
        return programStates.get(0);
    }

    @Override
    public void logProgramStateExecute(ProgramState programState) throws MyException {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            printWriter.write(programState + "\n");
        }
        catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }
}
