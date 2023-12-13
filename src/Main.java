import controller.Controller;
import model.ADTs.*;
import model.ProgramState;
import model.exceptions.MyException;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
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

public class Main {
    public static void main(String[] args) {
        // View view = new View();


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
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))))))))));

        Statement example5 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new CompoundStatement(new NewStatement("a", new VariableExpression("v")), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));

        Statement example6 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new CompoundStatement(new NewStatement("a", new VariableExpression("v")), new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));

        Statement example7 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), new CompoundStatement(
                new NewStatement("v", new ValueExpression(new IntValue(20))), new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), new CompoundStatement(
                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))), new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new VariableExpression("v")),
                new ValueExpression(new IntValue(5))))))));


        Statement example8 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")))))))));

        Statement example9 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(
                new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), 5),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement(
                                "v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        IMyStack<Statement> stack1 = new MyStack<>();
        stack1.push(example1);
        IMyStack<Statement> stack2 = new MyStack<>();
        stack2.push(example2);
        IMyStack<Statement> stack3 = new MyStack<>();
        stack3.push(example3);
        IMyStack<Statement> stack4 = new MyStack<>();
        stack4.push(example4);
        IMyStack<Statement> stack5 = new MyStack<>();
        stack5.push(example5);
        IMyStack<Statement> stack6 = new MyStack<>();
        stack6.push(example6);
        IMyStack<Statement> stack7 = new MyStack<>();
        stack7.push(example7);
        IMyStack<Statement> stack8 = new MyStack<>();
        stack8.push(example8);
        IMyStack<Statement> stack9 = new MyStack<>();
        stack9.push(example9);

        ProgramState programState1 = new ProgramState(stack1, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<>());
        ProgramState programState2 = new ProgramState(stack2, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<>());
        ProgramState programState3 = new ProgramState(stack3, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<>());
        ProgramState programState4 = new ProgramState(stack4, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<>());
        ProgramState programState5 = new ProgramState(stack5, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<>());
        ProgramState programState6 = new ProgramState(stack6, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<>());
        ProgramState programState7 = new ProgramState(stack7, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<>());
        ProgramState programState8 = new ProgramState(stack8, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<>());
        ProgramState programState9 = new ProgramState(stack9, new MyDictionary<>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<>());

        try {
            IRepository repository1 = new Repository("src/log1.txt");
            IRepository repository2 = new Repository("src/log2.txt");
            IRepository repository3 = new Repository("src/log3.txt");
            IRepository repository4 = new Repository("src/log4.txt");
            IRepository repository5 = new Repository("src/log5.txt");
            IRepository repository6 = new Repository("src/log6.txt");
            IRepository repository7 = new Repository("src/log7.txt");
            IRepository repository8 = new Repository("src/log8.txt");
            IRepository repository9 = new Repository("src/log9.txt");

            repository1.add(programState1);
            repository2.add(programState2);
            repository3.add(programState3);
            repository4.add(programState4);
            repository5.add(programState5);
            repository6.add(programState6);
            repository7.add(programState7);
            repository8.add(programState8);
            repository9.add(programState9);

            Controller controller1 = new Controller(repository1);
            Controller controller2 = new Controller(repository2);
            Controller controller3 = new Controller(repository3);
            Controller controller4 = new Controller(repository4);
            Controller controller5 = new Controller(repository5);
            Controller controller6 = new Controller(repository6);
            Controller controller7 = new Controller(repository7);
            Controller controller8 = new Controller(repository8);
            Controller controller9 = new Controller(repository9);

            TextMenu textMenu = new TextMenu();

            textMenu.addCommand(new ExitCommand("0", "exit"));
            textMenu.addCommand(new RunExample("1", example1.toString(), controller1));
            textMenu.addCommand(new RunExample("2", example2.toString(), controller2));
            textMenu.addCommand(new RunExample("3", example3.toString(), controller3));
            textMenu.addCommand(new RunExample("4", example4.toString(), controller4));
            textMenu.addCommand(new RunExample("5", example5.toString(), controller5));
            textMenu.addCommand(new RunExample("6", example6.toString(), controller6));
            textMenu.addCommand(new RunExample("7", example7.toString(), controller7));
            textMenu.addCommand(new RunExample("8", example8.toString(), controller8));
            textMenu.addCommand(new RunExample("9", example9.toString(), controller9));

            textMenu.show();

        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }
}