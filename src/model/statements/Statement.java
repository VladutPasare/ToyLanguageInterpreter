package model.statements;
import model.ADTs.IMyDictionary;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.types.Type;

public interface Statement {
    ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTException;
    Statement deepCopy();
    IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws StatementException, ExpressionException;
}