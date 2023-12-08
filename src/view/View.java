package view;

import controller.Controller;
import model.exceptions.MyException;
import repository.IRepository;
import repository.Repository;
import java.util.Scanner;

public class View {
    Controller controller;

    public View() {
        try {
            IRepository repository = new Repository("file.txt");
            controller = new Controller(repository);

            printMenu();

            Scanner scanner = new Scanner(System.in);
            System.out.print(">> ");
            int option = scanner.nextInt();

            try {
                controller.examples(option);
                controller.allSteps();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
    void printMenu() {
        System.out.println("Example 1: int v;v=2;print(v);");
        System.out.println("Example 2: int a;int b;a=2+3*5;b=a+1;print(b)");
        System.out.println("Example 3: bool a;int v;a=true;(If a Then v=2 Else v=3);print(v)");
        System.out.println("Example 4: string varf; varf=\"testin\";openRFile(varf); int varc;" +
                "readFile(varf, varc);print(varc); ");
    }
}
