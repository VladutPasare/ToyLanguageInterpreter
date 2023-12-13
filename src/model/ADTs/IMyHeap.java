package model.ADTs;

import model.exceptions.ADTException;

import java.util.Map;

public interface IMyHeap<K, V> {
    int allocate(K freeLocation, V value);
    void setContent(Map<K, V> content);
    Map<K, V> getContent();
    void update(K address, V value);
    V getValue(K address);
    boolean contains(K address);
    Integer getFreeLocation();

}
