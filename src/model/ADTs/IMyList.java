package model.ADTs;

import model.exceptions.ADTException;

public interface IMyList<T> {
    public void add(T element);
    public T get(int index) throws ADTException;
    public void remove(int index) throws ADTException;
}
