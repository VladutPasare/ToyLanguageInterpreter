package model.ADTs;

import model.exceptions.ADTException;

import java.util.Map;

public interface IMyDictionary<K, V> {
    void put(K key, V value) throws ADTException;
    Map<K, V> getContent();
    V lookUp(K key);
    void remove(K key) throws ADTException;
    void update(K key, V newValue);
    boolean isDefined(K key);
    IMyDictionary<K, V> deepCopy();
}
