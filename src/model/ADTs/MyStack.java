package model.ADTs;
import model.exceptions.ADTException;
import model.statements.Statement;

import java.util.Stack;

public class MyStack<T> implements IMyStack<T> {
    private Stack<T> stack;

    public MyStack () {
        stack = new Stack<>();
    }

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public T pop() throws ADTException {
        if(stack.empty())
            throw new ADTException("Stack is empty!");
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (T element: stack)
            s.append(element.toString()).append("\n");
        return s.toString();
    }
}
