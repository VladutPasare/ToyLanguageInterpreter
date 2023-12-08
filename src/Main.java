import controller.Controller;
import model.ADTs.IMyStack;
import model.ADTs.MyDictionary;
import model.ADTs.MyList;
import model.ADTs.MyStack;
import model.ProgramState;
import model.exceptions.MyException;
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
import model.values.Value;
import repository.IRepository;
import repository.Repository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;
import view.View;

import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) {
        //View view = new View();


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


        IMyStack<Statement> stack1 = new MyStack<>();
        stack1.push(example1);
        IMyStack<Statement> stack2 = new MyStack<>();
        stack2.push(example2);
        IMyStack<Statement> stack3 = new MyStack<>();
        stack3.push(example3);
        IMyStack<Statement> stack4 = new MyStack<>();
        stack4.push(example4);

        ProgramState programState1 = new ProgramState(stack1, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>());
        ProgramState programState2 = new ProgramState(stack2, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>());
        ProgramState programState3 = new ProgramState(stack3, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>());
        ProgramState programState4 = new ProgramState(stack4, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>());

        try {
            IRepository repository1 = new Repository("log1.txt");
            IRepository repository2 = new Repository("log2.txt");
            IRepository repository3 = new Repository("log3.txt");
            IRepository repository4 = new Repository("log4.txt");

            repository1.add(programState1);
            repository2.add(programState2);
            repository3.add(programState3);
            repository4.add(programState4);


            Controller controller1 = new Controller(repository1);
            Controller controller2 = new Controller(repository2);
            Controller controller3 = new Controller(repository3);
            Controller controller4 = new Controller(repository4);

            TextMenu textMenu = new TextMenu();

            textMenu.addCommand(new ExitCommand("0", "exit"));
            textMenu.addCommand(new RunExample("1", example1.toString(), controller1));
            textMenu.addCommand(new RunExample("2", example2.toString(), controller2));
            textMenu.addCommand(new RunExample("3", example3.toString(), controller3));
            textMenu.addCommand(new RunExample("4", example4.toString(), controller4));
            textMenu.show();

        } catch (MyException e) {
            throw new RuntimeException(e);
        }


    }
}