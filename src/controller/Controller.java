package controller;

import model.ADTs.IMyStack;
import model.ADTs.MyDictionary;
import model.ADTs.MyList;
import model.ADTs.MyStack;
import model.ProgramState;
import model.exceptions.ADTException;
import model.exceptions.ExpressionException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.ArithmeticExpression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;

import java.util.Random;

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

        System.out.println("\u001B[36m" +programState);

        repository.logProgramStateExecute(programState);
        while(!programState.getExecutionStack().isEmpty()) {
            oneStep(programState);
            System.out.println(programState); // flag
            repository.logProgramStateExecute(programState);
        }
    }

    public void examples(int option) {
        IMyStack<Statement> stack = new MyStack<>();

        Statement example1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        Statement example2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',
                                new ValueExpression(new IntValue(2)), new ArithmeticExpression('*',
                                new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(
                                        '+', new VariableExpression("a"), new ValueExpression(new IntValue(1))
                                )), new PrintStatement(new VariableExpression("b"))))));

        Statement example3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(
                                new BoolValue(true))), new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new AssignmentStatement("v",
                                new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        Statement example4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("testin.txt"))),
                        new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),
                                                "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))))))))));

        switch (option) {
            case 1: {
                stack.push(example1);
                break;
            }

            case 2: {
                stack.push(example2);
                break;
            }
            case 3: {
                stack.push(example3);
                break;
            }
            case 4: {
                stack.push(example4);
                break;
            }
            default:
        }
        ProgramState programState = new ProgramState(stack, new MyDictionary<>(), new MyList<>());
        repository.add(programState);
    }
}
