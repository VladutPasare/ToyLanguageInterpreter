package model.statements;
import model.ADTs.IMyDictionary;
import model.ADTs.IMyStack;
import model.ProgramState;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.types.Type;

public class CompoundStatement implements Statement {
    private final Statement first;
    private final Statement second;

    public CompoundStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public ProgramState execute(ProgramState state) {
        IMyStack<Statement> executionStack = state.getExecutionStack();
        executionStack.push(second);
        executionStack.push(first);
        return null;
    }

    @Override
    public String toString() {
        return '(' + first.toString() + ';' + second.toString() + ')';
    }

    @Override
    public CompoundStatement deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public IMyDictionary<String, Type> typeCheck(IMyDictionary<String, Type> typeEnv) throws StatementException, ExpressionException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }
}
