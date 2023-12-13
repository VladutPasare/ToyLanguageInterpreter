package model.ADTs;

import model.exceptions.ADTException;

public interface IMyList<T> {
    void add(T element);
    T get(int index) throws ADTException;
    void remove(int index) throws ADTException;
}
