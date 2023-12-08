package model.ADTs;

import model.exceptions.ADTException;

public interface IMyStack<T> {
    public void push(T element);
    public T pop() throws ADTException;
    public boolean isEmpty();
}