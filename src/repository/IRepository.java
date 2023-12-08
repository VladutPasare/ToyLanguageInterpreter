package repository;

import model.ProgramState;
import model.exceptions.MyException;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    public ProgramState getCurrentProgram();
    public void add(ProgramState state);
    public List<ProgramState> getProgramStatesList();

    public void logProgramStateExecute(ProgramState programState) throws MyException;
}