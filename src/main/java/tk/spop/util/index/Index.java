package tk.spop.util.index;

import java.util.*;

import lombok.RequiredArgsConstructor;
import tk.spop.util.Reflections;


@RequiredArgsConstructor
public class Index<K, V> {

    public final String field;
    public final Map<K, List<V>> map = new HashMap<>();


    public Index(String field, List<V> values) {
        this(field);
        addAll(values);
    }


    public void add(V value) {

        @SuppressWarnings("unchecked") K key = (K) Reflections.get(value, field);

        List<V> list = map.get(key);
        if (list == null) {
            list = new ArrayList<>();
            map.put(key, list);
        }
        list.add(value);
    }


    public void addAll(Collection<V> values) {
        for (V value : values) {
            add(value);
        }
    }


    public List<V> get(K key) {
        List<V> list = map.get(key);
        if (list != null) {
            return new ArrayList<>(list);
        }
        return new ArrayList<>();
    }


    public V getOne(K key) {
        List<V> list = map.get(key);
        if (list != null) {
            return list.get(0);
        }
        return null;
    }


    public Collection<K> getKeys() {
        return map.keySet();
    }


    public void reIndex() {
        Collection<List<V>> list = map.values();
        map.clear();
        for (List<V> values : list) {
            addAll(values);
        }
    }

}
