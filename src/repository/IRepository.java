package repository;

import model.ProgramState;
import model.exceptions.MyException;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    ProgramState getCurrentProgram();
    void add(ProgramState state);
    List<ProgramState> getProgramStatesList();

    void logProgramStateExecute(ProgramState programState) throws MyException;
}