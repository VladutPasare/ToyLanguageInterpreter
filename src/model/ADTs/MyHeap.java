package model.ADTs;

import model.exceptions.ADTException;
import model.values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<K, V> implements IMyHeap<K, V> {
    Map <K, V> map;
    Integer nextAddress;

    public MyHeap () {
        map = new HashMap<>();
        nextAddress = 1;
    }
    @Override
    public int allocate(K freeLocation, V value) {
        map.put(freeLocation, value);
        return nextAddress++;
    }
    @Override
    public void setContent(Map<K, V> content) {
        map = content;
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public V getValue(K address) {
        return map.get(address);
    }

    @Override
    public boolean contains(K address) {
        return map.containsKey(address);
    }

    public void update(K address, V value) {
        map.put(address, value);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        String st = "";
        for (K i : map.keySet())
            s.append(i.toString()).append("-->").append(map.get(i)).append("\n");
        return s.toString();
    }

    @Override
    public Integer getFreeLocation() {
        return nextAddress;
    }
}
