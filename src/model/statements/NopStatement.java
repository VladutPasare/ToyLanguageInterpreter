package model.statements;

import model.ADTs.IMyDictionary;
import model.ProgramState;
import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.types.Type;

public class NopStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new NopStatement();
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        return typeEnv;
    }
}
