package model.statements;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;

public interface Statement {
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTException;
    public Statement deepCopy();
}