package model.ADTs;
import model.exceptions.ADTException;

import java.util.Map;
import java.util.HashMap;

public class MyDictionary<K, V> implements IMyDictionary<K, V> {
    private final Map<K, V> map;

    public MyDictionary () {
        map = new HashMap<>();
    }

    @Override
    public void put(K key, V value) throws ADTException {
        if(map.containsKey(key))
            throw new ADTException("Dictionary already contains the key " + key + '!');
        map.put(key, value);
    }

    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public V lookUp(K key){
        return map.get(key);
    }

    @Override
    public void remove(K key) throws ADTException {
        if(!map.containsKey(key))
            throw new ADTException("Dictionary does not contain the key " + key + '!');
        map.remove(key);
    }

    @Override
    public void update(K key, V newValue) {
        map.put(key, newValue);
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for(K key: map.keySet())
            s.append(key.toString()).append("-->").append(map.get(key)).append("\n");
        return s.toString();
    }
}
