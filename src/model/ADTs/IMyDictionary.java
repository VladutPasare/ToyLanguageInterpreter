package model.ADTs;

import model.exceptions.ADTException;

public interface IMyDictionary<K, V> {
    public void put(K key, V value) throws ADTException;
    public V lookUp(K key);
    public void remove(K key) throws ADTException;
    public void update(K key, V newValue);
    public boolean isDefined(K key);
}
