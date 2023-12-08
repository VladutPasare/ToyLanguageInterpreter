package model.ADTs;
import model.exceptions.ADTException;

import java.util.ArrayList;

public class MyList<T> implements IMyList<T> {
    private ArrayList<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public T get(int index) throws ADTException {
        if(!validIndex(index))
            throw new ADTException("Index is out of range!");
        return list.get(index);
    }

    @Override
    public void remove(int index) throws ADTException {
        if(!validIndex(index))
            throw new ADTException("Index is out of range!");
        list.remove(index);
    }

    private boolean validIndex(int index) {
        return 0 <= index && index < list.size();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for(T element: list)
            s.append(element.toString()).append("\n");
        return s.toString();
    }
}
