package model.ADTs;

import model.exceptions.ADTException;

public interface IMyStack<T> {
    void push(T element);
    T pop() throws ADTException;
    boolean isEmpty();
}